package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;


public class ArrayStorage extends AbstractArrayStorage {

    protected void saveResume(Resume r, int index) {
        storage[size] = r;
    }

    protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
    }

    protected int getIndex(String uuid) {
        for (int index = 0; index < size; index++) {
            if (storage[index].getUuid().equals(uuid)) {
                return index;
            }
        }
        return -1;
    }
}
