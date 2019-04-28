package com.thunder.webapp.sql;

import com.thunder.webapp.exception.ExistStorageException;
import com.thunder.webapp.exception.StorageException;

import java.sql.*;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public interface SqlExecutor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    public void executeSqlQuery(String query, SqlExecutor sqlExecutor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            sqlExecutor.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23505")) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }

    public interface SqlExecutorQuery<T> {
        T executeQuery(PreparedStatement ps) throws SQLException;
    }

    public <T> T executeSqlQueryWithReturn(String query, SqlExecutorQuery<T> sqlExecutorQuery) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return sqlExecutorQuery.executeQuery(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}