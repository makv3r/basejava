package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;


public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void doSave(Resume r, Object key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    public void doUpdate(Resume r, Object key) {
        storage.replace((String) key, r);
    }

    @Override
    public void doDelete(Object key) {
        storage.remove(key);
    }

    @Override
    public Resume doGet(Object key) {
        return storage.get(key);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkKey(Object key) {
        return storage.containsKey(key);
    }
}