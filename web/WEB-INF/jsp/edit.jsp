<%@ page import="com.thunder.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="resume" type="com.thunder.webapp.model.Resume" scope="request"/>
<jsp:include page="fragments/header.jsp">
    <jsp:param name="title" value="Resume ${resume.fullName}"/>
</jsp:include>

<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <table class="MainTable">
            <thead>
            <tr>
                <th>${resume.fullName}</th>
                <th>
                    <button type="submit" class="button">Save</button>
                    <button onclick="window.history.back()" class="button">Cancel</button>
                </th>
            </tr>
            </thead>
            <tbody>
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <tr>
                <td><b>Имя</b></td>
                <td><input type="text" name="fullName" value="${resume.fullName}" required></td>
            </tr>

            <c:forEach var="contactType" items="<%=ContactType.values()%>">
                <tr>
                    <td><b>${contactType.title}</b></td>
                    <td><input type="text" name="${contactType.name()}" size=50
                               value="${resume.getContact(contactType)}"></td>
                </tr>
            </c:forEach>

            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <c:choose>
                    <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
                        <tr>
                            <td><b>${sectionType.title}</b></td>
                            <td><input type="text" id="${sectionType.name()}" name="${sectionType.name()}"
                                       value="${resume.getSection(sectionType)}"></td>
                        </tr>
                    </c:when>

                    <c:when test="${sectionType.equals(SectionType.ACHIEVEMENTS) || sectionType.equals(SectionType.QUALIFICATIONS)}">
                        <tr>
                            <td><b>${sectionType.title}</b></td>
                            <td>
                                <textarea rows="15" id="${sectionType.name()}"name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea>
                            </td>
                        </tr>
                    </c:when>

                    <c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">
                    </c:when>

                </c:choose>
            </c:forEach>
            </tbody>
        </table>
    </form>

</section>

<jsp:include page="fragments/footer.jsp"/>