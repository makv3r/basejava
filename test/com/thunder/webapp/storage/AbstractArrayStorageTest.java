package com.thunder.webapp.storage;

import com.thunder.webapp.exception.ExistStorageException;
import com.thunder.webapp.exception.NotExistStorageException;
import com.thunder.webapp.exception.StorageException;
import com.thunder.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public abstract class AbstractArrayStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(4, storage.size());
        Assert.assertNotNull(storage.get(UUID_4));
    }

    @Test
    public void update() throws Exception {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_1));
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
        Assert.assertNotNull(storage);
    }

    @Test
    public void get() throws Exception {
        Resume r = storage.get(UUID_1);
        Assert.assertNotNull(r);
        Assert.assertEquals(r, storage.get(UUID_1));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = storage.getAll();
        Assert.assertNotNull(resumes);
        Assert.assertEquals(3, resumes.length);
        Assert.assertEquals(resumes[0], storage.get(UUID_1));
        Assert.assertEquals(resumes[1], storage.get(UUID_2));
        Assert.assertEquals(resumes[2], storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExistException() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void getStackOverflowException() throws Exception {
        for (int i = 0; i < 10_000; i++) {
            storage.save(new Resume());
        }
    }
}