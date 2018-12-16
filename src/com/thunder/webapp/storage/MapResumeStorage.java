package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;


public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    public void doDelete(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    public Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean checkSearchKey(Resume resume) {
        return resume != null;
    }
}