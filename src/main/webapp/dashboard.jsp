<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.peerreview.model.Project" %>
<%@ page import="com.peerreview.dao.ProjectDAO" %>
<%@ page import="com.peerreview.dao.ReviewDAO" %>
<%@ page import="com.peerreview.dao.UserDAO" %>

<%
String email = (String) session.getAttribute("userEmail");

if(email == null){
    response.sendRedirect("login.jsp");
    return;
}

ProjectDAO projectDAO = new ProjectDAO();
ReviewDAO reviewDAO = new ReviewDAO();
UserDAO userDAO = new UserDAO();

int totalProjects = projectDAO.getTotalProjects();
int totalReviews = reviewDAO.getTotalReviews();
int totalUsers = userDAO.getTotalUsers();

List<Project> recentProjects = projectDAO.getAllProjects();
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
      <!--   <li><a href="submitProject.html">Submit Project</a></li>
        <li><a href="myProjects">My Projects</a></li> -->
        <li><a href="viewProjects">View Projects</a></li>
        <li><a href="profile">Profile</a><li>
        <li><a href="logout">Logout</a></li>
    </ul>
</nav>
<main>
<section class="dashboard">
    <h1>Welcome, <span class="username"><%= session.getAttribute("userName") %></span> 👋</h1>
    <p>Track your projects, explore peer work, and manage feedback in one place.</p>

    <div class="cards">
        <div class="card">
            <h3>📤 Submit Project</h3>
            <p>Upload your project for review.</p>
            <a href="submitProject.html">Submit</a>
        </div>

        <div class="card">
            <h3>📁 My Projects</h3>
            <p>Check feedback on your projects</p>
            <a href="myProjects">View</a>
        </div>

        <div class="card">
            <h3>🌐 View Projects</h3>
            <p>See projects submitted by other</p>
            <a href="viewProjects">View</a>
        </div>

        <div class="card">
            <h3>⭐ Your Reviews</h3>
            <p>Check feedback on your projects.</p>
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
    <%
    if(recentProjects != null && !recentProjects.isEmpty()){
        int limit = Math.min(3, recentProjects.size());
        for(int i = 0; i < limit; i++){
            Project p = recentProjects.get(i);

            String desc = p.getDescription();
            if(desc != null && desc.length() > 90){
                desc = desc.substring(0, 90) + "...";
            }
    %>

    <!-- FULL CARD CLICKABLE -->
    <div class="recent-card clickable-card"
         onclick="window.location.href='viewProjects'">

        <h3><%= p.getTitle() %></h3>
        <p><%= desc %></p>

    </div>

    <%
        }
    } else {
    %>

    <div class="recent-card">
        <h3>No Projects Yet</h3>
        <p>Start by submitting your first project.</p>
    </div>

    <%
    }
    %>
</div>
</section>
</main>

<footer>
    <p>© 2026 Peer Review Portal | Built by Group No 11</p>
</footer>

</body>
</html>