<%@ page import="com.thunder.webapp.model.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% request.setCharacterEncoding("UTF-8"); %>

<jsp:include page="fragments/header.jsp" flush="true">
    <jsp:param name="title" value="Resumes List"/>
</jsp:include>

<section>
    <table class="MainTable">
        <thead>
        <tr>
            <th>NAME</th>
            <th>TEL</th>
            <th>SKYPE</th>
            <th>MAIL</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${ContactType.TEL.toHtml(resume.getContact(ContactType.TEL))}</td>
                <td>${ContactType.SKYPE.toHtml(resume.getContact(ContactType.SKYPE))}</td>
                <td>${ContactType.MAIL.toHtml(resume.getContact(ContactType.MAIL))}</td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit" class="button">EDIT</a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete" class="button">DELETE</a></td>
            </tr>
        </c:forEach>
        <tbody>
    </table>
</section>

<jsp:include page="fragments/footer.jsp"/>