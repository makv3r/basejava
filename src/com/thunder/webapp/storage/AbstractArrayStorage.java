package com.thunder.webapp.storage;

import com.thunder.webapp.exception.StorageException;
import com.thunder.webapp.model.Resume;

import java.util.Arrays;


public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final static int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doSave(Resume r, Object key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Stop It! Size will exceed limit.", r.getUuid());
        } else {
            saveResume(r, (Integer) key);
            size++;
        }
    }

    @Override
    public void doUpdate(Resume r, Object key) {
        storage[(Integer) key] = r;
    }

    @Override
    public void doDelete(Object key) {
        deleteResume((Integer) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Object key) {
        return storage[(Integer) key];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract void saveResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract Object getIndex(String uuid);
}