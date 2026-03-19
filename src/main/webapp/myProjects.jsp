<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.peerreview.model.Project"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Projects</title>
<link rel="stylesheet" href="css/viewProject.css">
</head>
<body class="view-project-page">

<h1 style="text-align:center">My Uploaded Projects</h1>

<%
List<Project> projects = (List<Project>) request.getAttribute("projects");

if(projects != null && !projects.isEmpty()){
    for(Project p : projects){
%>

<div class="project-card">

    <h2><%= p.getTitle() %></h2>
    <p><%= p.getDescription() %></p>

    <div class="slider">
        <button class="arrow left" onclick="slide(this,-1)">❮</button>

        <div class="slider-track">
            <%
            List<String[]> mediaList = p.getMediaList();
            if (mediaList != null && !mediaList.isEmpty()) {
                for (String[] media : mediaList) {
                    String fileName = media[0];
                    String mediaType = media[1];
                    String encodedFile = URLEncoder.encode(fileName, "UTF-8");

                    if ("image".equalsIgnoreCase(mediaType)) {
            %>
                        <img class="media-item"
                             src="<%=request.getContextPath()%>/uploads?file=<%=encodedFile%>"
                             onclick="openPopup(this.src)">
            <%
                    } else if ("video".equalsIgnoreCase(mediaType)) {
            %>
                        <video class="media-item" controls>
                            <source src="<%=request.getContextPath()%>/uploads?file=<%=encodedFile%>">
                        </video>
            <%
                    }
                }
            }
            %>
        </div>

        <button class="arrow right" onclick="slide(this,1)">❯</button>
    </div>

    <p>
        <a href="<%= p.getGithubLink() %>" target="_blank">View GitHub Project</a>
    </p>

    <p><b>Submitted by:</b> <%= p.getStudentEmail() %></p>

    <div class="actions">
        <a href="editProject?id=<%= p.getId() %>" class="action-btn">✏ Edit</a>

        <form action="deleteProject" method="post" style="display:inline;">
            <input type="hidden" name="projectId" value="<%= p.getId() %>">
            <button type="submit" class="action-btn"
                    onclick="return confirm('Are you sure you want to delete this project?');">
                🗑 Delete
            </button>
        </form>
    </div>

</div>

<%
    }
} else {
%>

<h3 style="text-align:center; margin-top:40px;">No projects uploaded yet.</h3>

<%
}
%>

<div id="popup" class="popup">
    <span onclick="closePopup()">✖</span>
    <img id="popupImg">
</div>

<script src="<%=request.getContextPath()%>/js/projectSlider.js"></script>
</body>
</html>