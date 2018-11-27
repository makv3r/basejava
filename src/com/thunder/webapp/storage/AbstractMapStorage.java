package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractMapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    protected abstract Object getKey(String uuid);

    protected abstract boolean checkKey(Object key);

    public abstract void doDelete(Object key);

    public abstract Resume doGet(Object key);

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
        storage.replace(r.getUuid(), r);
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }
}