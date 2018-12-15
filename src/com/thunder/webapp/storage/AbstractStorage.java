package com.thunder.webapp.storage;

import com.thunder.webapp.exception.ExistStorageException;
import com.thunder.webapp.exception.NotExistStorageException;
import com.thunder.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getKey(String uuid);

    protected abstract void doSave(Resume r, SK key);

    protected abstract void doUpdate(Resume r, SK key);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract boolean checkKey(SK key);

    protected abstract List<Resume> getAll();

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK key = getNotExistKey(r.getUuid());
        doSave(r, key);
    }

    public void update(Resume r) {
        LOG.info("Update " + r);
        SK key = getExistKey(r.getUuid());
        doUpdate(r, key);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = getExistKey(uuid);
        doDelete(key);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = getExistKey(uuid);
        return doGet(key);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    private SK getExistKey(String uuid) {
        SK key = getKey(uuid);
        if (!checkKey(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getNotExistKey(String uuid) {
        SK key = getKey(uuid);
        if (checkKey(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}