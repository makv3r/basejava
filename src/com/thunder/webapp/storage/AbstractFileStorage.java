package com.thunder.webapp.storage;

import com.thunder.webapp.exception.StorageException;
import com.thunder.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("NullPointerException size()", directory.getAbsolutePath());
        }
        return list.length;
    }

    @Override
    public void clear() {
        File[] filesArray = directory.listFiles();

        if (filesArray != null) {
            for (File file : filesArray) {
                doDelete(file);
            }
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            boolean tmp = file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error doSave()", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error doUpdate()", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        Resume resume;
        try {
            resume = doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error doGet()", file.getName(), e);
        }
        return resume;
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error doDelete()", file.getName());
        }
    }

    @Override
    protected boolean checkSearchKey(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> list = new ArrayList<>();
        File[] filesArray = directory.listFiles();

        if (filesArray == null) {
            throw new StorageException("NullPointerException getAll()", directory.getAbsolutePath());
        }

        for (File file : filesArray) {
            list.add(doGet(file));
        }

        return list;
    }
}
