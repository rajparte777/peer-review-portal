<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.peerreview.model.Project"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/profile.css">
</head>
<body class="profile-page">

<nav class="navbar">
    <h2 class="logo">PeerReview</h2>
    <ul>
        <li><a href="dashboard.jsp">Dashboard</a></li>
        <li><a href="viewProjects">Projects</a></li>
        <li><a href="myProjects">My Projects</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>
</nav>

<div class="profile-wrapper">

    <div class="profile-header-card">
        <div class="profile-avatar">
    <%
    String profilePhoto = (String) request.getAttribute("profilePhoto");
    if (profilePhoto != null && !profilePhoto.trim().isEmpty()) {
    %>
        <img src="<%=request.getContextPath()%>/profilePhoto?file=<%= profilePhoto %>" alt="Profile Photo">
    <%
    } else {
    %>
        <span>👤</span>
    <%
    }
    %>
</div>

        <div class="profile-main-info">
            <h1>${name}</h1>
            <p class="profile-email">${email}</p>
            <p class="profile-role">PeerReview Portal Member</p>

            <form action="uploadProfilePhoto" method="post" enctype="multipart/form-data" class="photo-upload-form">
                <input type="file" name="profilePhoto" accept="image/*" required>
                <button type="submit">Upload Photo</button>
            </form>
        </div>
    </div>

    <div class="stats-grid">
        <div class="stat-card">
            <h3>Total Projects</h3>
            <p>${projectCount}</p>
        </div>

        <div class="stat-card">
            <h3>Total Likes</h3>
            <p>${totalLikes}</p>
        </div>

        <div class="stat-card">
            <h3>Total Reviews</h3>
            <p>${totalReviews}</p>
        </div>

        <div class="stat-card">
            <h3>Average Rating</h3>
            <p>${avgRating}</p>
        </div>
    </div>

    <div class="section-card">
        <div class="section-top">
            <h2>Your Projects</h2>
            <span class="badge">${projectCount} Projects</span>
        </div>

        <%
        List<Project> projects = (List<Project>) request.getAttribute("projects");

        if (projects != null && !projects.isEmpty()) {
            for (Project p : projects) {
        %>
            <div class="project-item">
                <div class="project-text">
                    <h3><%= p.getTitle() %></h3>
                    <p>
                        <%= p.getDescription() != null && p.getDescription().length() > 140
                            ? p.getDescription().substring(0, 140) + "..."
                            : p.getDescription() %>
                    </p>

                    <div class="project-links">
                        <% if (p.getGithubLink() != null && !p.getGithubLink().trim().isEmpty()) { %>
                            <a href="<%= p.getGithubLink() %>" target="_blank">🔗 View Project</a>
                        <% } %>

                        <% if (p.getGithubProfile() != null && !p.getGithubProfile().trim().isEmpty()) { %>
                            <a href="<%= p.getGithubProfile() %>" target="_blank">👤 GitHub Profile</a>
                        <% } %>
                    </div>
                </div>
            </div>
        <%
            }
        } else {
        %>
            <p class="empty-text">No projects uploaded yet.</p>
        <%
        }
        %>
    </div>

</div>

</body>
</html>