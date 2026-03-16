<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="com.peerreview.model.Project"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Projects</title>
<link rel="stylesheet" href="css/viewProject.css">
</head>
<body>

<h1 style="text-align:center">My Uploaded Projects</h1>

<%
List<Project> projects = (List<Project>) request.getAttribute("projects");

if(projects != null && !projects.isEmpty()){
    for(Project p : projects){
%>

<div class="project-card">

    <h2><%= p.getTitle() %></h2>
    <p><%= p.getDescription() %></p>

    <!-- MEDIA SLIDER -->
    <div class="slider">
        <button class="arrow left" onclick="slide(this,-1)">❮</button>

        <div class="slider-track">
            <%
            List<String[]> mediaList = p.getMediaList();
            if (mediaList != null && !mediaList.isEmpty()) {
                for (String[] media : mediaList) {
                    String fileName = media[0];
                    String mediaType = media[1];

                    if ("image".equalsIgnoreCase(mediaType)) {
            %>
                        <img class="media-item"
                             src="<%=request.getContextPath()%>/uploads/<%=fileName%>"
                             onclick="openPopup(this.src)">
            <%
                    } else if ("video".equalsIgnoreCase(mediaType)) {
            %>
                        <video class="media-item" controls>
                            <source src="<%=request.getContextPath()%>/uploads/<%=fileName%>">
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
        <button>✏ Edit</button>
        <button>🗑 Delete</button>
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

<!-- IMAGE POPUP -->
<div id="popup" class="popup">
    <span onclick="closePopup()">✖</span>
    <img id="popupImg">
</div>

<script src="<%=request.getContextPath()%>/js/projectSlider.js"></script>
</body>
</html>