package com.thunder.webapp.model;


import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<ContactType, ContactData> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, SectionData> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public Map<ContactType, ContactData> getContacts() {
        return contacts;
    }

    public Map<SectionType, SectionData> getSections() {
        return sections;
    }

    public void setContacts(ContactType type, ContactData data) {
        contacts.put(type,data);
    }

    public void setSections(SectionType type, SectionData data) {
        sections.put(type,data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        int tmp = fullName.compareTo(o.getFullName());
        return tmp != 0 ? tmp : uuid.compareTo(o.getUuid());
    }
}
