package com.thunder.webapp.storage;

import com.thunder.webapp.exception.StorageException;
import com.thunder.webapp.model.Resume;
import com.thunder.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FileStorage extends AbstractStorage<File> {
    private StreamSerializer streamSerializer;
    private File directory;


    public FileStorage(File directory, StreamSerializer streamSerializer) {
        Objects.requireNonNull(directory, "Directory must not be null");
        Objects.requireNonNull(streamSerializer, "StreamSerializer must mot be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.streamSerializer = streamSerializer;
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
            file.createNewFile();
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error doSave()", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error doUpdate()", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error doGet()", file.getName(), e);
        }
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

    private void doWrite(Resume resume, OutputStream os) throws IOException {
        streamSerializer.doWrite(resume, os);
    }

    private Resume doRead(InputStream is) throws IOException {
        return streamSerializer.doRead(is);
    }
}