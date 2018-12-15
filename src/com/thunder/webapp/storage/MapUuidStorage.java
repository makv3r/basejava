package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;


public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    public void doDelete(String key) {
        storage.remove(key);
    }

    @Override
    public Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkKey(String key) {
        return storage.containsKey(key);
    }
}