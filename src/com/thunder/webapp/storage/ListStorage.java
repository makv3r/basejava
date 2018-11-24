package com.thunder.webapp.storage;


import com.thunder.webapp.model.Resume;

import java.util.LinkedList;
import java.util.List;


public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new LinkedList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume r, Object index) {
        storage.add(r);
    }

    @Override
    public void doUpdate(Resume r, Object key) {
        storage.set((int) key, r);
    }

    @Override
    public void doDelete(Object key) {
        storage.remove((int) key);
    }

    @Override
    public Resume doGet(Object key) {
        return storage.get((int) key);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    protected Object getKey(String uuid) {
        int index = 0;
        for (Resume tmp : storage) {
            if (tmp.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    protected boolean checkKey(Object key) {
        return (int) key >= 0;
    }
}