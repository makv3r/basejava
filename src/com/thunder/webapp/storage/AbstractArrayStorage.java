package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int i = getIndex(uuid);

        if (i < 0) { // more universal for getIndex
            System.out.println("Resume " + uuid + " not found.");
            return null;
        }
        return storage[i];
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);
}
