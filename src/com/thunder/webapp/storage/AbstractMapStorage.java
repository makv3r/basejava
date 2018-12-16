package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {

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
    public void doSave(Resume resume, SK searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(Resume resume, SK searchKey) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }
}