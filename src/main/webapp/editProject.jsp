<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.peerreview.model.Project"%>

<%
String email = (String) session.getAttribute("userEmail");

if(email == null){
    response.sendRedirect("login.jsp");
    return;
}

Project p = (Project) request.getAttribute("project");
if(p == null){
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
</head>
<body>

<nav class="navbar">
    <h2>PeerReview</h2>
</nav>

<div class="form-container">
    <h2>Edit Project</h2>

    <form action="editProject" method="post">
        <input type="hidden" name="id" value="<%= p.getId() %>">

        <input type="text" name="title" value="<%= p.getTitle() %>" required>

        <textarea name="description" required><%= p.getDescription() %></textarea>

        <input type="url" name="github" value="<%= p.getGithubLink() %>" required>

        <button type="submit">Update Project</button>
    </form>
</div>

</body>
</html>