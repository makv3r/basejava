package com.thunder.webapp.storage;

import com.thunder.webapp.exception.ExistStorageException;
import com.thunder.webapp.exception.NotExistStorageException;
import com.thunder.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = new Resume(UUID_1, "Гарри Поттер");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Гермиона Грейнджер");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Рон Уизли");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Северус Снейп");

    static {
        RESUME_1.setContacts(ContactType.TEL, new Link("+123456789", ""));
        RESUME_1.setContacts(ContactType.SKYPE, new Link("skype.name", "skype:skype.profile"));
        RESUME_1.setContacts(ContactType.MAIL, new Link("mail@mail.com", "mailto:mail@mail.com"));
        RESUME_1.setContacts(ContactType.PROFILE, new Link("Профиль 1", "https://google.com"));
        RESUME_1.setContacts(ContactType.LINK, new Link("Домашняя страница", "https://google.com"));
        RESUME_1.setSections(SectionType.OBJECTIVE, new TextSection("Objective description"));
        RESUME_1.setSections(SectionType.PERSONAL, new TextSection("Personal description"));
        RESUME_1.setSections(SectionType.ACHIEVEMENTS, new ListSection("Achievement 1", "Achievement 2"));
        RESUME_1.setSections(SectionType.QUALIFICATIONS, new ListSection("Qualification 1", "Qualification 2"));
        RESUME_1.setSections(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2018, 1, 1), LocalDate.now(),"Activity", "Description")),
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Activity", "Description"))
        ));
        RESUME_1.setSections(SectionType.EDUCATION, new OrganizationSection(
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2013, 3, 1),LocalDate.of(2013, 5, 1), "Activity","")),
                new Organization(new Link("Name", "http://google.com"), new Activity(LocalDate.of(2011, 3, 1),LocalDate.of(2011, 4, 1), "Activity",""))
        ));

        RESUME_2.setContacts(ContactType.TEL, new Link("+123456789", ""));
        RESUME_2.setContacts(ContactType.SKYPE, new Link("skype.name", "skype:skype.profile"));
        RESUME_2.setContacts(ContactType.MAIL, new Link("mail@mail.com", "mailto:mail@mail.com"));
        RESUME_2.setContacts(ContactType.PROFILE, new Link("Профиль 1", "https://google.com"));
        RESUME_2.setContacts(ContactType.LINK, new Link("Домашняя страница", "https://google.com"));
        RESUME_2.setSections(SectionType.OBJECTIVE, new TextSection("Objective description"));
        RESUME_2.setSections(SectionType.PERSONAL, new TextSection("Personal description"));
        RESUME_2.setSections(SectionType.ACHIEVEMENTS, new ListSection("Achievement 1", "Achievement 2"));
        RESUME_2.setSections(SectionType.QUALIFICATIONS, new ListSection("Qualification 1", "Qualification 2"));
        RESUME_2.setSections(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2018, 1, 1), LocalDate.now(),"Activity", "Description")),
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Activity", "Description"))
        ));
        RESUME_2.setSections(SectionType.EDUCATION, new OrganizationSection(
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2013, 3, 1),LocalDate.of(2013, 5, 1), "Activity","")),
                new Organization(new Link("Name", "http://google.com"), new Activity(LocalDate.of(2011, 3, 1),LocalDate.of(2011, 4, 1), "Activity",""))
        ));

        RESUME_3.setContacts(ContactType.TEL, new Link("+123456789", ""));
        RESUME_3.setContacts(ContactType.SKYPE, new Link("skype.name", "skype:skype.profile"));
        RESUME_3.setContacts(ContactType.MAIL, new Link("mail@mail.com", "mailto:mail@mail.com"));
        RESUME_3.setContacts(ContactType.PROFILE, new Link("Профиль 1", "https://google.com"));
        RESUME_3.setContacts(ContactType.LINK, new Link("Домашняя страница", "https://google.com"));
        RESUME_3.setSections(SectionType.OBJECTIVE, new TextSection("Objective description"));
        RESUME_3.setSections(SectionType.PERSONAL, new TextSection("Personal description"));
        RESUME_3.setSections(SectionType.ACHIEVEMENTS, new ListSection("Achievement 1", "Achievement 2"));
        RESUME_3.setSections(SectionType.QUALIFICATIONS, new ListSection("Qualification 1", "Qualification 2"));
        RESUME_3.setSections(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2018, 1, 1), LocalDate.now(),"Activity", "Description")),
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Activity", "Description"))
        ));
        RESUME_3.setSections(SectionType.EDUCATION, new OrganizationSection(
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2013, 3, 1),LocalDate.of(2013, 5, 1), "Activity","")),
                new Organization(new Link("Name", "http://google.com"), new Activity(LocalDate.of(2011, 3, 1),LocalDate.of(2011, 4, 1), "Activity",""))
        ));

        RESUME_4.setContacts(ContactType.TEL, new Link("+123456789", ""));
        RESUME_4.setContacts(ContactType.SKYPE, new Link("skype.name", "skype:skype.profile"));
        RESUME_4.setContacts(ContactType.MAIL, new Link("mail@mail.com", "mailto:mail@mail.com"));
        RESUME_4.setContacts(ContactType.PROFILE, new Link("Профиль 1", "https://google.com"));
        RESUME_4.setContacts(ContactType.LINK, new Link("Домашняя страница", "https://google.com"));
        RESUME_4.setSections(SectionType.OBJECTIVE, new TextSection("Objective description"));
        RESUME_4.setSections(SectionType.PERSONAL, new TextSection("Personal description"));
        RESUME_4.setSections(SectionType.ACHIEVEMENTS, new ListSection("Achievement 1", "Achievement 2"));
        RESUME_4.setSections(SectionType.QUALIFICATIONS, new ListSection("Qualification 1", "Qualification 2"));
        RESUME_4.setSections(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2018, 1, 1), LocalDate.now(),"Activity", "Description")),
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Activity", "Description"))
        ));
        RESUME_4.setSections(SectionType.EDUCATION, new OrganizationSection(
                new Organization(new Link("Name", "https://google.com"),new Activity(LocalDate.of(2013, 3, 1),LocalDate.of(2013, 5, 1), "Activity","")),
                new Organization(new Link("Name", "http://google.com"), new Activity(LocalDate.of(2011, 3, 1),LocalDate.of(2011, 4, 1), "Activity",""))
        ));

    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1, "Северус Снейп");
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() throws Exception {
        storage.get(UUID_4);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> resumes1 = storage.getAllSorted();
        List<Resume> resumes2 = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(resumes1.size(), resumes2.size());
        Assert.assertEquals(resumes1, resumes2);
    }
}