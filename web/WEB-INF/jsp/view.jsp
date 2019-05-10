<%@ page import="com.thunder.webapp.model.*" %>
<%@ page import="com.thunder.webapp.util.DateUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="resume" type="com.thunder.webapp.model.Resume" scope="request"/>
<jsp:include page="fragments/header.jsp">
    <jsp:param name="title" value="Resume ${resume.fullName}"/>
</jsp:include>

<section>
    <table class="MainTable">
        <thead>
        <tr>
            <th>${resume.fullName}</th>
            <th>
                <a href="resume?uuid=${resume.uuid}&action=edit" class="button">EDIT</a>
                <a href="resume?uuid=${resume.uuid}&action=delete" class="button">DELETE</a>
            </th>
        <tr>
        </thead>
        <tbody>

        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="Map.Entry<ContactType, String>"/>
            <tr>
                <td><b>${contactEntry.key.getTitle()}</b></td>
                <td>${contactEntry.getKey().toHtml(contactEntry.getValue())}</td>
            </tr>
        </c:forEach>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="Map.Entry<SectionType, AbstractSection>"/>
            <c:set var="sectionType" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="AbstractSection"/>

            <tr>
                <td><b>${sectionType.title}</b></td>

                <c:choose>
                    <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
                        <td><%=((TextSection) section).getText()%>
                        </td>
                    </c:when>

                    <c:when test="${sectionType.equals(SectionType.ACHIEVEMENTS) || sectionType.equals(SectionType.QUALIFICATIONS)}">
                        <td>
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getList()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </c:when>

                    <c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">
                        <td>
                            <ul>
                            <% List<Organization> organizations = ((OrganizationSection) sectionEntry.getValue()).getOrganizations(); %>
                            <c:if test="<%=organizations.size() != 0%>">
                                <c:forEach var="organization" items="<%=organizations%>">
                                    <c:set var="organizationName" value="${organization.link.name}"/>
                                    <c:if test="${organization.link.url.length() != 0}">
                                        <c:set var="organizationName" value="<a href='${organization.link.url}'>${organization.link.name}</a>"/>
                                    </c:if>
                                    <c:forEach var="activity" items="${organization.activities}">
                                        <jsp:useBean id="activity" type="Organization.Activity"/>
                                        <c:set var="startDate" value="${DateUtil.format(activity.startDate)}"/>
                                        <c:set var="endDate" value="${DateUtil.format(activity.endDate)}"/>
                                        <c:if test="${endDate == '01/3000'}">
                                            <c:set var="endDate" value="по настоящее время"/>
                                        </c:if>

                                        <li>
                                            ${organizationName}<b> : ${startDate} - ${endDate}</b><br>
                                            <b>${activity.title}</b><br>
                                            ${activity.description}<p>
                                        </li>
                                    </c:forEach>
                                </c:forEach>
                            </c:if>
                            </ul>
                        </td>
                    </c:when>

                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>

<jsp:include page="fragments/footer.jsp"/>