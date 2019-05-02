package com.thunder.webapp.sql;

import com.thunder.webapp.exception.ExistStorageException;
import com.thunder.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
//            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(e);
            }
        }
        return new StorageException(e);
    }
}
