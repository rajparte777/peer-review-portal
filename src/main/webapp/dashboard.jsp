<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%
String email = (String) session.getAttribute("userEmail");

if(email == null){
    response.sendRedirect("login.jsp");
    return;
}
%>
<%@ page import="com.peerreview.dao.ProjectDAO" %>
<%@ page import="com.peerreview.dao.ReviewDAO" %>
<%@ page import="com.peerreview.dao.UserDAO" %>

<%
ProjectDAO projectDAO = new ProjectDAO();
ReviewDAO reviewDAO = new ReviewDAO();
UserDAO userDAO = new UserDAO();

int totalProjects = projectDAO.getTotalProjects();
int totalReviews = reviewDAO.getTotalReviews();   // ✅ THIS LINE WAS MISSING
int totalUsers = userDAO.getTotalUsers();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link rel="stylesheet" href="css/dashboard.css">
</head>

<body>

<nav class="navbar">
    <h2>PeerReview</h2>

    <ul>
        <li><a href="index.html">Home</a></li>
        <li><a href="submitProject.html">Submit Project</a></li>
        <li><a href="myProjects">My Projects</a></li>
        <li><a href="viewProjects">View Projects</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>
</nav>

<section class="dashboard">
    <h1>Welcome <%= session.getAttribute("userName") %></h1>
    <p>Manage your projects and reviews</p>

    <div class="cards">
        <div class="card">
            <h3>Submit Project</h3>
            <p>Upload your project for review</p>
            <a href="submitProject.html">Submit</a>
        </div>

        <div class="card">
            <h3>My Projects</h3>
            <p>See projects uploaded by you</p>
            <a href="myProjects">View</a>
        </div>

        <div class="card">
            <h3>View Projects</h3>
            <p>See projects submitted by others</p>
            <a href="viewProjects">View</a>
        </div>

        <div class="card">
            <h3>Your Reviews</h3>
            <p>Check feedback on your projects</p>
            <a href="myReviews">Check</a>
        </div>
    </div>
</section>

<section class="stats">
 <div class="stat-box">
    <h2><%= totalProjects %>+</h2>
    <p>Projects Submitted</p>
</div>

<div class="stat-box">
    <h2><%= totalReviews %>+</h2>
    <p>Reviews Given</p>
</div>

<div class="stat-box">
    <h2><%= totalUsers %>+</h2>
    <p>Active Users</p>
</div>
</section>

<section class="recent">
    <h2>Recent Projects</h2>

    <div class="recent-container">
        <div class="recent-card">
            <h3>Library Management System</h3>
            <p>Java + MySQL</p>
        </div>

        <div class="recent-card">
            <h3>E-commerce Website</h3>
            <p>HTML CSS JS</p>
        </div>

        <div class="recent-card">
            <h3>Online Voting System</h3>
            <p>Servlet + JSP</p>
        </div>
    </div>
</section>

</body>
</html>