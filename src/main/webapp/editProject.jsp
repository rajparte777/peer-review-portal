<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.peerreview.model.Project" %>

<%
Project project = (Project) request.getAttribute("project");

if (project == null) {
    response.sendRedirect("myProjects");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Project</title>
<link rel="stylesheet" href="css/style.css">
<style>
.form-actions{
    display:flex;
    gap:12px;
    margin-top:10px;
}
.form-actions a{
    flex:1;
    text-align:center;
    text-decoration:none;
    padding:14px;
    border-radius:10px;
    font-weight:700;
    background:#e5e7eb;
    color:#111827;
}
.form-actions a:hover{
    background:#d1d5db;
}
.form-actions button{
    flex:1;
}
</style>
</head>
<body>

<nav class="navbar">
    <h2 class="logo">PeerReview</h2>
</nav>

<div class="form-container">
    <h2>Edit Project</h2>

    <form action="editProject" method="post">
    <input type="hidden" name="id" value="<%= project.getId() %>">
    <input type="text" name="title" value="<%= project.getTitle() %>" required>
    <textarea name="description" required><%= project.getDescription() %></textarea>
    <input type="url" name="githubLink" value="<%= project.getGithubLink() %>" required>
    <button type="submit">Update Project</button>
</form>
</div>

</body>
</html>