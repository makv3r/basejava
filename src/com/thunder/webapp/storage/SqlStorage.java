package com.thunder.webapp.storage;

import com.thunder.webapp.exception.NotExistStorageException;
import com.thunder.webapp.model.ContactType;
import com.thunder.webapp.model.Resume;
import com.thunder.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(uuid) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, conn);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                executeAndCheckResponse(resume.getUuid(), ps);
            }
            try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.execute();
            }
            insertContacts(resume, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            executeAndCheckResponse(uuid, ps);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        resume.addContact(
                                ContactType.valueOf(rs.getString("type")),
                                rs.getString("value")
                        );
                    } while (rs.next());

                    return resume;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();

        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    result.add(new Resume(rs.getString(1).trim(), rs.getString(2).trim()));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT uuid, full_name, type, value FROM resume r " +
                    "LEFT JOIN contact c " +
                    "ON r.uuid = c.resume_uuid " +
                    "ORDER BY full_name, uuid "
            )) {
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException("");
                }
                for (Resume resume : result) {
                    do {
                        if (!resume.getUuid().equals(rs.getString("uuid").trim())) break;
                        resume.addContact(
                                ContactType.valueOf(rs.getString("type")),
                                rs.getString("value")
                        );
                    } while (rs.next());
                }
            }

            return null;
        });

/**
 Верхний вариант выполняет работу в 2 запроса
 Нижний в N + 1 (где N количество resume)
 */

/*
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    result.add(new Resume(rs.getString(1).trim(), rs.getString(2).trim()));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                for (Resume resume : result) {
                    ps.setString(1, resume.getUuid());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resume.addContact(
                                ContactType.valueOf(rs.getString("type")),
                                rs.getString("value")
                        );
                    }
                }
            }

            return null;
        });
*/
        return result;
    }

    private void executeAndCheckResponse(String uuid, PreparedStatement ps) throws SQLException {
        if (ps.executeUpdate() != 1) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

}
