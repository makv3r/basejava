package com.thunder.webapp.storage;


import com.thunder.webapp.model.Resume;

import java.util.Iterator;
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

    protected Object getIndex(String uuid) {
        int index = 0;
        Iterator<Resume> iterator = storage.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}