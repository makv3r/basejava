package com.thunder.webapp.storage;

import com.thunder.webapp.storage.serializer.ObjectStreamStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStorage()));
    }

}
