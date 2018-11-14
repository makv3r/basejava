package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Stop It! Size will exceed limit.");
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.println("Resume " + r.getUuid() + " already exist.");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        int i = getIndex(r.getUuid());
        if (i < 0) {
            System.out.println("Resume " + r.getUuid() + " not found.");
        } else {
            storage[i] = r;
        }
    }

    public void delete(String uuid) {
        int i = getIndex(uuid);

        if (i < 0) {
            System.out.println("Resume " + uuid + " not found.");
        } else {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
