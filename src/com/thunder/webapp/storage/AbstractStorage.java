package com.thunder.webapp.storage;

import com.thunder.webapp.exception.ExistStorageException;
import com.thunder.webapp.exception.NotExistStorageException;
import com.thunder.webapp.model.Resume;


public abstract class AbstractStorage implements Storage {

    protected abstract Object getIndex(String uuid);

    protected abstract void doSave(Resume r, Object key);

    protected abstract void doUpdate(Resume r, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    public void save(Resume r) {
        Object key = getKey(true, r.getUuid());
        doSave(r, key);
    }

    public void update(Resume r) {
        Object key = getKey(false, r.getUuid());
        doUpdate(r, key);
    }

    public void delete(String uuid) {
        Object key = getKey(false, uuid);
        doDelete(key);
    }

    public Resume get(String uuid) {
        Object key = getKey(false, uuid);
        return doGet(key);
    }

    private Object getKey(boolean contain, String uuid) {
        Object key = getIndex(uuid);

        if (key instanceof String) {
            if (contain) throw new ExistStorageException(uuid);
        } else if (contain && (int) key >= 0) {
            throw new ExistStorageException(uuid);
        } else if (!contain && (int) key < 0) {
            throw new NotExistStorageException(uuid);
        }

        return key;
    }
}