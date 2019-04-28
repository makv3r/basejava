package com.thunder.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist.", uuid);
    }

    public ExistStorageException(Exception e) {
        super(e.getMessage(), e);
    }
}
