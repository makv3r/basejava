package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ListStorage extends AbstractStorage<Integer> {

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
    protected void doSave(Resume r, Integer index) {
        storage.add(r);
    }

    @Override
    public void doUpdate(Resume r, Integer key) {
        storage.set(key, r);
    }

    @Override
    public void doDelete(Integer key) {
        storage.remove(key.intValue());
    }

    @Override
    public Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage);
    }

    protected Integer getKey(String uuid) {
        int index = 0;
        for (Resume tmp : storage) {
            if (tmp.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    protected boolean checkKey(Integer key) {
        return key >= 0;
    }
}