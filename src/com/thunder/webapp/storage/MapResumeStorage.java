package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;


public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    public void doDelete(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    public Resume doGet(Resume key) {
        return key;
    }

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean checkKey(Resume key) {
        return key != null;
    }
}