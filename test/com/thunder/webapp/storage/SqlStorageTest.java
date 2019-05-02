package com.thunder.webapp.storage;

import com.thunder.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }

}
