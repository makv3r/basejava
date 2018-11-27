package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;


public class MapResumeStorage extends AbstractMapStorage {

    @Override
    public void doDelete(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    public Resume doGet(Object key) {
        return (Resume) key;
    }

    @Override
    protected Object getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean checkKey(Object key) {
        return key != null;
    }
}