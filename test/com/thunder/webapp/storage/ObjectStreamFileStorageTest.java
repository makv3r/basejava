package com.thunder.webapp.storage;

import com.thunder.webapp.storage.serializer.ObjectStreamStorage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }

}
