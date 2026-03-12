<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="com.peerreview.model.Project"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Projects</title>

<link rel="stylesheet" href="css/viewProject.css">



</head>

<body>

<h1 style="text-align:center">Submitted Projects</h1>

<%

List<Project> projects = (List<Project>) request.getAttribute("projects");

if(projects != null){

for(Project p : projects){

%>

<div class="project-card">

<h2><%= p.getTitle() %></h2>

<p><%= p.getDescription() %></p>

<!-- MEDIA SLIDER -->

<div class="slider">

<button class="arrow left" onclick="slide(this,-1)">❮</button>

<div class="slider-track">

<% if(p.getImage1()!=null && !p.getImage1().equals("")){ %>

<img 
class="media-item"
src="<%=request.getContextPath()%>/uploads/<%= p.getImage1() %>"
onclick="openPopup(this.src)"
>

<% } %>

<% if(p.getImage2()!=null && !p.getImage2().equals("")){ %>

<img 
class="media-item"
src="<%=request.getContextPath()%>/uploads/<%= p.getImage2() %>"
onclick="openPopup(this.src)"
>

<% } %>

<% if(p.getVideo()!=null && !p.getVideo().equals("")){ %>

<video class="media-item" controls>

<source src="<%=request.getContextPath()%>/uploads/<%= p.getVideo() %>" type="video/mp4">

</video>

<% } %>

</div>

<button class="arrow right" onclick="slide(this,1)">❯</button>

<!-- DOT INDICATOR -->

<div class="dots"></div>

</div>

<p>

<a href="<%= p.getGithubLink() %>" target="_blank">

View GitHub Project

</a>

</p>

<p>

Submitted by: <%= p.getStudentEmail() %>

</p>

<div class="actions">

<button>👍 Like</button>

<button>💬 Comment</button>

<button>⭐ Review</button>

</div>

</div>

<%

}

}

%>

<!-- IMAGE POPUP -->

<div id="popup" class="popup">

<span onclick="closePopup()">✖</span>

<img id="popupImg">

</div>

<!-- JS FILE -->

<script src="<%=request.getContextPath()%>/js/projectSlider.js"></script>

</body>
</html>