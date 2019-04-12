package com.thunder.webapp;

import com.thunder.webapp.model.*;

import java.time.LocalDate;


public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = ResumeTestData.fillResume("uuid1","Григорий Кислин");

        System.out.println(resume);
    }

    public static Resume fillResume(String uuid, String name) {
        Resume resume  = new Resume(uuid, name);

        resume.setContacts(ContactType.TEL, new Link("+7(921) 855-0482", ""));
        resume.setContacts(ContactType.SKYPE, new Link("grigory.kislin", "skype:grigory.kislin"));
        resume.setContacts(ContactType.MAIL, new Link("gkislin@yandex.ru", "mailto:gkislin@yandex.ru"));
        resume.setContacts(ContactType.PROFILE, new Link("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin"));
        resume.setContacts(ContactType.PROFILE, new Link("Профиль GitHub", "https://github.com/gkislin"));
        resume.setContacts(ContactType.PROFILE, new Link("Профиль Stackoverflow", "https://stackoverflow.com/users/548473"));
        resume.setContacts(ContactType.LINK, new Link("Домашняя страница", "http://gkislin.ru/"));

        resume.setSections(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям."));
        resume.setSections(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.setSections(SectionType.ACHIEVEMENTS, new ListSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        ));

        resume.setSections(SectionType.QUALIFICATIONS, new ListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов",
                "проектрирования, архитектурных шаблонов, UML, функционального",
                "программирования",
                "Родной русский, английский \"upper intermediate\""
        ));

        resume.setSections(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(
                        new Link("Coursera", "https://www.coursera.org/course/progfun"),
                        new Organization.Activity(
                                LocalDate.of(2013, 10, 1),
                                LocalDate.now(),
                                "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок."
                        )),
                new Organization(
                        new Link("Wrike", "https://www.wrike.com/"),
                        new Organization.Activity(
                                LocalDate.of(2014, 10, 1),
                                LocalDate.of(2016, 1, 1),
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."
                        )),
                new Organization(
                        new Link("RIT Center", ""),
                        new Organization.Activity(
                                LocalDate.of(2012, 4, 1),
                                LocalDate.of(2014, 10, 1),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"
                        )),
                new Organization(
                        new Link("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"),
                        new Organization.Activity(
                                LocalDate.of(2010, 12, 1),
                                LocalDate.of(2012, 4, 1),
                                "Ведущий программист",
                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."
                        )),
                new Organization(
                        new Link("Yota", "https://www.yota.ru/"),
                        new Organization.Activity(
                                LocalDate.of(2008, 6, 1),
                                LocalDate.of(2010, 12, 1),
                                "Ведущий специалист",
                                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"
                        )),
                new Organization(
                        new Link("Enkata", "http://enkata.com/"),
                        new Organization.Activity(
                                LocalDate.of(2007, 3, 1),
                                LocalDate.of(2008, 6, 1),
                                "Разработчик ПО",
                                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."
                        )),
                new Organization(
                        new Link("Siemens AG", "https://www.siemens.com/ru/ru/home.html"),
                        new Organization.Activity(
                                LocalDate.of(2005, 1, 1),
                                LocalDate.of(2007, 2, 1),
                                "Разработчик ПО",
                                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."
                        )),
                new Organization(
                        new Link("Alcatel", "http://www.alcatel.ru/"),
                        new Organization.Activity(
                                LocalDate.of(1997, 9, 1),
                                LocalDate.of(2005, 1, 1),
                                "Инженер по аппаратному и программному тестированию",
                                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."
                        ))
        ));

        resume.setSections(SectionType.EDUCATION, new OrganizationSection(
                new Organization(
                        new Link("Coursera", "https://www.coursera.org/course/progfun"),
                        new Organization.Activity(
                                LocalDate.of(2013, 3, 1),
                                LocalDate.of(2013, 5, 1),
                                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                                ""
                        )),
                new Organization(
                        new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                        new Organization.Activity(
                                LocalDate.of(2011, 3, 1),
                                LocalDate.of(2011, 4, 1),
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                                ""
                        )),
                new Organization(
                        new Link("Siemens AG", "http://www.siemens.ru/"),
                        new Organization.Activity(
                                LocalDate.of(2005, 1, 1),
                                LocalDate.of(2005, 4, 1),
                                "3 месяца обучения мобильным IN сетям (Берлин)",
                                ""
                        )),
                new Organization(
                        new Link("Alcatel", "http://www.alcatel.ru/"),
                        new Organization.Activity(
                                LocalDate.of(1997, 9, 1),
                                LocalDate.of(1998, 3, 1),
                                "6 месяцев обучения цифровым телефонным сетям (Москва)",
                                ""
                        )),
                new Organization(
                        new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/"),
                        new Organization.Activity(
                                LocalDate.of(1993, 9, 1),
                                LocalDate.of(1996, 7, 1),
                                "Аспирантура (программист С, С++)",
                                ""
                        ),
                        new Organization.Activity(
                                LocalDate.of(1987, 9, 1),
                                LocalDate.of(1993, 7, 1),
                                "Инженер (программист Fortran, C)",
                                ""
                        )),
                new Organization(
                        new Link("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/"),
                        new Organization.Activity(
                                LocalDate.of(1984, 9, 1),
                                LocalDate.of(1987, 6, 1),
                                "Закончил с отличием",
                                ""
                        ))
        ));

        return resume;
    }
}
