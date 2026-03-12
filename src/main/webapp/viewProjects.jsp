<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.peerreview.model.Project"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Projects</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<h1 style="text-align:center">Submitted Projects</h1>

<%
List<Project> projects = (List<Project>) request.getAttribute("projects");

if(projects != null){
for(Project p : projects){
%>

<div style="width:60%;margin:auto;border:1px solid #ccc;padding:20px;margin-top:20px;border-radius:8px;">

<h2><%= p.getTitle() %></h2>

<p><%= p.getDescription() %></p>

<p>
<a href="<%= p.getGithubLink() %>" target="_blank">
View GitHub Project
</a>
</p>

<p>
Submitted by: <%= p.getStudentEmail() %>
</p>

</div>

<%
}
}
%>

</body>
</html>