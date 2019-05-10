<%@ page import="com.thunder.webapp.model.*" %>
<%@ page import="com.thunder.webapp.util.DateUtil" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
                    <td><input type="text" name="${contactType.name()}" value="${resume.getContact(contactType)}"></td>
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
                        <tr>
                            <td><b>${sectionType.title}</b></td>
                            <td>
                                <c:forEach items="${(resume.getSection(sectionType)).getOrganizations()}" var="organization" varStatus="counter">
                                    <br>
                                    <table class="MainTable"><tbody>
                                     <tr>
                                        <th>Название</th>
                                        <th><input type="text" value="${organization.getLink().getName()}" name="${sectionType}"></th>
                                    </tr>
                                    <tr>
                                        <th>Сайт</th>
                                        <th><input type="text" value="${organization.getLink().getUrl()}" name="${sectionType}url"></th>
                                    </tr>
                                    <c:forEach items="${organization.getActivities()}" var="activity">
                                        <jsp:useBean id="activity" type="Organization.Activity"/>
                                        <tr>
                                            <td>От / До</td>
                                            <td>
                                                <input class="DATE" type="text" value="${DateUtil.format(activity.startDate)}" name="${sectionType}${counter.index}startDate" placeholder="MM/yyyy">
                                                <input class="DATE" type="text" value="${DateUtil.format(activity.endDate)}" name="${sectionType}${counter.index}endDate" placeholder="MM/yyyy">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Должность</td>
                                            <td><input type="text" value="${activity.getTitle()}" name="${sectionType}${counter.index}title"></td>
                                        </tr>
                                        <tr>
                                            <td>Описание</td>
                                            <td><textarea name="${sectionType}${counter.index}description" rows=5>${activity.getDescription()}</textarea></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody></table><br>
                                </c:forEach>

                            </td>
                        </tr>
                    </c:when>

                </c:choose>
            </c:forEach>
            </tbody>
        </table>
    </form>

</section>

<jsp:include page="fragments/footer.jsp"/>