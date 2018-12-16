package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;


public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    public void doDelete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkSearchKey(String uuid) {
        return storage.containsKey(uuid);
    }
}