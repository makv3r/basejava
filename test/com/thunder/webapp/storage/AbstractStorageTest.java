package com.thunder.webapp.storage;

import com.thunder.webapp.exception.ExistStorageException;
import com.thunder.webapp.exception.NotExistStorageException;
import com.thunder.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public abstract class AbstractStorageTest {
    protected Storage storage;

    protected static final String NAME_1 = "Гарри Поттер";
    protected static final String NAME_2 = "Гермиона Грейнджер";
    protected static final String NAME_3 = "Рон Уизли";
    protected static final String NAME_4 = "Северус Снейп";

    protected static final Resume RESUME_1 = new Resume(NAME_1);
    protected static final Resume RESUME_2 = new Resume(NAME_2);
    protected static final Resume RESUME_3 = new Resume(NAME_3);
    protected static final Resume RESUME_4 = new Resume(NAME_4);


    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(storage.get(RESUME_4.getUuid()), RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(RESUME_1.getUuid(),RESUME_1.getFullName()));
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(RESUME_1.getUuid(),NAME_4);
        storage.update(resume);
        Assert.assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(NAME_1);
        Assert.assertEquals(2, storage.size());
        storage.get(NAME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(NAME_4);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void get() throws Exception {
        Resume resume = storage.get(RESUME_1.getUuid());
        Assert.assertEquals(resume, RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() throws Exception {
        storage.get(NAME_4);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> resumes1 = storage.getAllSorted();
        List<Resume> resumes2 = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(resumes1.size(), resumes2.size());
        Assert.assertEquals(resumes1, resumes2);
    }
}