package com.thunder.webapp.storage;

import com.thunder.webapp.exception.ExistStorageException;
import com.thunder.webapp.exception.NotExistStorageException;
import com.thunder.webapp.model.Resume;

import java.util.Collections;
import java.util.List;


public abstract class AbstractStorage implements Storage {

    protected abstract Object getKey(String uuid);

    protected abstract void doSave(Resume r, Object key);

    protected abstract void doUpdate(Resume r, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract boolean checkKey(Object key);

    protected abstract List<Resume> getAll();

    public void save(Resume r) {
        Object key = getNotExistKey(r.getUuid());
        doSave(r, key);
    }

    public void update(Resume r) {
        Object key = getExistKey(r.getUuid());
        doUpdate(r, key);
    }

    public void delete(String uuid) {
        Object key = getExistKey(uuid);
        doDelete(key);
    }

    public Resume get(String uuid) {
        Object key = getExistKey(uuid);
        return doGet(key);
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    private Object getExistKey(String uuid) {
        Object key = getKey(uuid);
        if (!checkKey(key)) throw new NotExistStorageException(uuid);
        return key;
    }

    private Object getNotExistKey(String uuid) {
        Object key = getKey(uuid);
        if (checkKey(key)) throw new ExistStorageException(uuid);
        return key;
    }
}