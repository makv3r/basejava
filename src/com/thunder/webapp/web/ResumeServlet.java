package com.thunder.webapp.web;

import com.thunder.webapp.Config;
import com.thunder.webapp.ResumeTestData;
import com.thunder.webapp.model.*;
import com.thunder.webapp.storage.Storage;
import com.thunder.webapp.util.DateUtil;
import com.thunder.webapp.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume resume;
        boolean isNewResume = uuid.isEmpty();
        if (isNewResume) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            String[] values = request.getParameterValues(sectionType.name());
            if (StringUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(sectionType);
            } else {
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(request.getParameter(sectionType.name())));
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(value.split("\r\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(sectionType.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!StringUtil.isEmpty(name)) {
                                List<Organization.Activity> activity = new ArrayList<>();
                                String pfx = sectionType.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] allPositions = request.getParameterValues(pfx + "title");
                                String[] allInformation = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < allPositions.length; j++) {
                                    if (!StringUtil.isEmpty(allPositions[j])) {
                                        activity.add(new Organization.Activity(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), allPositions[j], allInformation[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(name, urls[i]), activity));
                            }
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        if (isNewResume) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "create":
                resume = Resume.EMPTY;
                break;
            case "generate":
                storage.save(ResumeTestData.fillResume(UUID.randomUUID().toString(), StringUtil.generateString()));
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    AbstractSection section = resume.getSection(sectionType);
                    switch (sectionType) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENTS:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationsSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (organizationsSection != null) {
                                for (Organization org : organizationsSection.getOrganizations()) {
                                    List<Organization.Activity> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Organization.Activity.EMPTY);
                                    emptyFirstPositions.addAll(org.getActivities());
                                    emptyFirstOrganizations.add(new Organization(org.getLink(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            break;

                    }
                    resume.addSection(sectionType, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

}


