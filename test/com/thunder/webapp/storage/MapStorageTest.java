package com.thunder.webapp.storage;

import com.thunder.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;


public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes1 = storage.getAll();
        Resume[] resumes2 = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertEquals(resumes1.length, resumes2.length);
        //Assert.assertArrayEquals(resumes1, resumes2);

        for (int i = 0; i < resumes1.length; i++) {
            boolean findEqual = false;
            for (int j = 0; j < resumes2.length; j++) {
                if (Objects.equals(resumes1[i], resumes2[j])) {
                    findEqual = true;
                    break;
                }
            }
            if (!findEqual) Assert.fail("Arrays contains not equal elements.");
        }
    }
}
