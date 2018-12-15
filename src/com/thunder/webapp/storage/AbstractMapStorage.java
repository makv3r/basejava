package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {

    protected Map<String, Resume> storage = new HashMap<>();

    protected abstract SK getKey(String uuid);

    protected abstract boolean checkKey(SK key);

    public abstract void doDelete(SK key);

    public abstract Resume doGet(SK key);

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void doSave(Resume r, SK key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    public void doUpdate(Resume r, SK key) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }
}