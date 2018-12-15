package com.thunder.webapp.storage;

import com.thunder.webapp.exception.StorageException;
import com.thunder.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;


public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    public void doSave(Resume r, Integer key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Stop It! Size will exceed limit.", r.getUuid());
        } else {
            saveResume(r, key);
            size++;
        }
    }

    @Override
    public void doUpdate(Resume r, Integer key) {
        storage[key] = r;
    }

    @Override
    public void doDelete(Integer key) {
        deleteResume(key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Integer key) {
        return storage[key];
    }

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected boolean checkKey(Integer key) {
        return key >= 0;
    }

    protected abstract void saveResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract Integer getKey(String uuid);
}