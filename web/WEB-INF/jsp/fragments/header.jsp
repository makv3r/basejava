<%@ page contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title><% out.print(request.getParameter("title")); %></title>
</head>
<header>
    <a href="resume" class="button">Resumes List</a>
    <a href="resume?action=create" class="button">New Resume</a>
    <a href="resume?action=generate" class="button">Generate Resume</a>
</header>
<body>
<p>