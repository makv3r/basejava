package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;


public class MapUuidStorage extends AbstractMapStorage {

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
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkKey(Object key) {
        return storage.containsKey(key);
    }
}