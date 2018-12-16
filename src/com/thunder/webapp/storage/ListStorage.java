package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ListStorage extends AbstractStorage<Integer> {

    protected List<Resume> storage = new LinkedList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    public void doUpdate(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    public void doDelete(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage);
    }

    protected Integer getSearchKey(String uuid) {
        int index = 0;
        for (Resume resume : storage) {
            if (resume.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    protected boolean checkSearchKey(Integer searchKey) {
        return searchKey >= 0;
    }
}