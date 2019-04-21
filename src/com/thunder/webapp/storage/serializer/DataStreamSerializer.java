package com.thunder.webapp.storage.serializer;

import com.thunder.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeCollection(dos, resume.getContacts().entrySet(), contactsEntry -> {
                dos.writeUTF(contactsEntry.getKey().name());
                dos.writeUTF(contactsEntry.getValue());
            });

            writeCollection(dos, resume.getSections().entrySet(), sectionsEntry -> {
                SectionType sectionType = sectionsEntry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) sectionsEntry.getValue()).getText());
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection) sectionsEntry.getValue()).getList(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeCollection(dos, ((OrganizationSection) sectionsEntry.getValue()).getOrganizations(), organization -> {
                            dos.writeUTF(organization.getLink().getName());
                            dos.writeUTF(organization.getLink().getUrl());
                            writeCollection(dos, organization.getActivities(), activity -> {
                                dos.writeUTF(activity.getStartDate().toString());
                                dos.writeUTF(activity.getEndDate().toString());
                                dos.writeUTF(activity.getTitle());
                                dos.writeUTF(activity.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readMap(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readMap(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(readList(dis, dis::readUTF)));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        resume.addSection(type, new OrganizationSection(readList(dis, () -> new Organization(
                                new Link(dis.readUTF(), dis.readUTF()),
                                readList(dis, () -> new Organization.Activity(
                                        LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()),
                                        dis.readUTF(),
                                        dis.readUTF()))))));
                        break;
                }
            });
            return resume;
        }
    }

    private interface DataWriter<T> {
        void write(T data) throws IOException;
    }

    private interface DataReader<T> {
        T read() throws IOException;
    }

    private interface MapReader {
        void readAndInitializeMap() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, DataWriter<T> dataWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T data : collection) {
            dataWriter.write(data);
        }
    }

    private <T> List<T> readList(DataInputStream dis, DataReader<T> dataReader) throws IOException {
        int listSize = dis.readInt();
        List<T> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(dataReader.read());
        }
        return list;
    }

    private void readMap(DataInputStream dis, MapReader mapReader) throws IOException {
        int mapSize = dis.readInt();
        for (int i = 0; i < mapSize; i++) {
            mapReader.readAndInitializeMap();
        }
    }
}
