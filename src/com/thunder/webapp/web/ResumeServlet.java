package com.thunder.webapp.web;

import com.thunder.webapp.Config;
import com.thunder.webapp.model.*;
import com.thunder.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


public class ResumeServlet extends HttpServlet {
    private Storage storage = Config.getInstance().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        //String name = request.getParameter("name");
        //response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!");

        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<link rel=\"stylesheet\" href=\"css/style.css\">");
            out.println("<title>Resumes Table</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Resumes Table</h3>");

            List<Resume> list = storage.getAllSorted();
            for (Resume resume : list) {
                Map<ContactType, String> contacts = resume.getContacts();
                Map<SectionType, AbstractSection> sections = resume.getSections();

                out.println("<section><table class=\"MainTable\">");

                out.println("<thead><tr>");
                out.println("<th>" + resume.getUuid() + "</th>");
                out.println("<th>" + resume.getFullName() + "</th>");
                out.println("</tr></thead><tbody>");

                for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                    out.println("<tr>");
                    out.println("<td>" + entry.getKey().getTitle() + "</td>");
                    out.println("<td>" + entry.getValue() + "</td>");
                    out.println("</tr>");
                }

                for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                    out.println("<tr>");
                    out.println("<td>" + entry.getKey().getTitle() + "</td>");
                    out.println("<td>");
                    SectionType sectionType = SectionType.valueOf(entry.getKey().toString());
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            out.println(entry.getValue().toString());
                            break;
                        case ACHIEVEMENTS:
                        case QUALIFICATIONS:
                            List<String> items = (((ListSection) entry.getValue()).getList());
                            out.println("<ul>");
                            for (String line : items) {
                                out.println("<li>" + line + "</li>");
                            }
                            out.println("</ul>");
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            break;
                    }
                    out.println("</td>");
                    out.println("</tr>");
                }


                out.println("<tbody></table></section><br><br><br>");
            }
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }
}


