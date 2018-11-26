package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;

import java.util.List;

public interface Storage {

    int size();

    void clear();

    void save(Resume r);

    void update(Resume r);

    void delete(String uuid);

    Resume get(String uuid);

    List<Resume> getAllSorted();
}
