package com.thunder.webapp.storage;

import com.thunder.webapp.exception.NotExistStorageException;
import com.thunder.webapp.model.*;
import com.thunder.webapp.sql.SqlHelper;
import com.thunder.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
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
            insertSections(resume, conn);
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
            deleteContacts(resume, conn);
            deleteSections(resume, conn);
            insertContacts(resume, conn);
            insertSections(resume, conn);
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
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT uuid, full_name, c.type, c.value FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid " +
                    "     WHERE r.uuid = ? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(resume, rs);
                } while (rs.next());
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT type, value FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs);
                }
            }

            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * FROM resume ORDER BY full_name, uuid"
            )) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString(1).trim();
                    String full_name = rs.getString(2);
                    map.computeIfAbsent(uuid, k -> new Resume(k, full_name));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT resume_uuid, type, value FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid").trim();
                    Resume resume = map.get(uuid);
                    addContact(resume, rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT resume_uuid, type, value FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid").trim();
                    Resume resume = map.get(uuid);
                    addSection(resume, rs);
                }
            }

            return new ArrayList<>(map.values());
        });
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

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                ps.setString(1, resume.getUuid());
                ps.setString(2, sectionType.name());
                ps.setString(3, JsonParser.write(section, AbstractSection.class));

                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            resume.addSection(type, JsonParser.read(value, AbstractSection.class));
        }
    }

    private void deleteContacts(Resume resume, Connection conn) throws SQLException {
        deleteAttributes(resume, conn, "DELETE FROM contact WHERE resume_uuid = ?");
    }

    private void deleteSections(Resume resume, Connection conn) throws SQLException {
        deleteAttributes(resume, conn, "DELETE FROM section WHERE resume_uuid = ?");
    }

    private void deleteAttributes(Resume resume, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}
