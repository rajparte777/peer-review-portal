<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.peerreview.model.Project"%>
<%@ page import="com.peerreview.dao.InteractionDAO"%>
<%@ page import="com.peerreview.model.Comment"%>
<%@ page import="com.peerreview.model.Review"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Projects</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/viewProject.css">
<link rel="stylesheet" href="css/myProject.css">


</head>
<body class="view-project-page">

<nav class="navbar">
    <h2 class="logo">PeerReview</h2>
    <ul>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="dashboard.jsp">Dash Board</a></li>
        <li><a href="viewProjects">View Projects</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>
</nav>

<h1 class="my-projects-title">My Uploaded Projects</h1>

<%
String msg = request.getParameter("msg");
if ("updated".equals(msg)) {
%>
    <div class="success-msg">
        ✅ Project Updated Successfully!
    </div>
<%
}

List<Project> projects = (List<Project>) request.getAttribute("projects");
InteractionDAO interactionDAO = new InteractionDAO();

if(projects != null && !projects.isEmpty()){
    for(Project p : projects){

        int likeCount = interactionDAO.getLikeCount(p.getId());
        int commentCount = interactionDAO.getCommentCount(p.getId());
        int reviewCount = interactionDAO.getReviewCount(p.getId());
        double avgRating = interactionDAO.getAverageRating(p.getId());

        List<Comment> comments = interactionDAO.getCommentsByProjectId(p.getId());
        List<Review> reviews = interactionDAO.getReviewsByProjectId(p.getId());
%>

<div class="project-card">

    <h2><%= p.getTitle() %></h2>
    <%
String fullDesc = p.getDescription();
String shortDesc = fullDesc;

if (fullDesc != null && fullDesc.length() > 180) {
    shortDesc = fullDesc.substring(0, 180) + "...";
}
%>

<p class="project-desc">
    <span id="short-desc-<%= p.getId() %>" <%= (fullDesc != null && fullDesc.length() > 180) ? "" : "style='display:inline;'" %>>
        <%= shortDesc %>
    </span>

    <% if (fullDesc != null && fullDesc.length() > 180) { %>
        <span id="full-desc-<%= p.getId() %>" style="display:none;">
            <%= fullDesc %>
        </span>

        <a href="javascript:void(0);" class="read-more-btn"
           onclick="toggleDescription(<%= p.getId() %>)"
           id="toggle-btn-<%= p.getId() %>">
            Read More
        </a>
    <% } %>
</p>

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
     onclick="openProjectPopup(this)">
     
                    } else if ("video".equalsIgnoreCase(mediaType)) {
            %>
                       <video class="media-item" controls onclick="openProjectPopup(this)">
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

  <div class="project-links">
    <% if (p.getGithubLink() != null && !p.getGithubLink().trim().isEmpty()) { %>
        <p class="project-link-text">
            🔗 <a href="<%= p.getGithubLink() %>" target="_blank">View Project</a>
        </p>
    <% } %>

    <% if (p.getGithubProfile() != null && !p.getGithubProfile().trim().isEmpty()) { %>
        <p class="project-link-text">
            👤 <a href="<%= p.getGithubProfile() %>" target="_blank">GitHub Profile</a>
        </p>
    <% } %>
</div>



    <p class="submitted-by"><b>Submitted by:</b> <%= p.getStudentEmail() %></p>

    <div class="actions">
        <a href="editProject?id=<%= p.getId() %>" class="action-btn edit-btn">✏ Edit</a>

        <form action="deleteProject" method="post" class="delete-form">
            <input type="hidden" name="projectId" value="<%= p.getId() %>">
            <button type="submit" class="action-btn delete-btn"
                    onclick="return confirm('Are you sure you want to delete this project?');">
                🗑 Delete
            </button>
        </form>
    </div>

    <div class="feedback-summary">
    
    <button type="button" class="summary-item like-item" disabled>
        👍 <%= likeCount %> Likes
    </button>

    <button type="button" class="summary-item"
        onclick="toggleSection('comments-<%= p.getId() %>')">
        💬 Comments (<%= commentCount %>)
    </button>

    <button type="button" class="summary-item"
        onclick="toggleSection('reviews-<%= p.getId() %>')">
        ⭐ Reviews (<%= reviewCount %>)
    </button>

</div>

<div class="project-section feedback-detail" id="comments-<%= p.getId() %>">
        <h3>Comments Received (<%= commentCount %>)</h3>

        <div class="comment-list">
            <%
            if(comments != null && !comments.isEmpty()){
                for(Comment c : comments){
            %>
                <div class="comment-box">
                    <p><b><%= c.getUserEmail() %></b></p>
                    <p><%= c.getComment() %></p>
                </div>
            <%
                }
            } else {
            %>
                <p class="no-comment">No comments received yet.</p>
            <%
            }
            %>
        </div>
    </div>

 <div class="project-section feedback-detail" id="reviews-<%= p.getId() %>">
        <h3>
            Reviews Received (<%= reviewCount %>) |
            Average Rating: <%= String.format("%.1f", avgRating) %> / 5
        </h3>

        <div class="review-list">
            <%
            if(reviews != null && !reviews.isEmpty()){
                for(Review r : reviews){
            %>
                <div class="review-box">
                    <p>
                        <b><%= r.getReviewerEmail() %></b> -
                        <%
                        int rating = r.getRating();
                        for(int i = 1; i <= 5; i++){
                            if(i <= rating){
                        %>★<%
                            } else {
                        %>☆<%
                            }
                        }
                        %>
                    </p>
                    <p><%= r.getReviewText() %></p>
                </div>
            <%
                }
            } else {
            %>
                <p class="no-review">No reviews received yet.</p>
            <%
            }
            %>
        </div>
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
    <span class="popup-close" onclick="closePopup()">✖</span>

    <button type="button" class="popup-arrow popup-left" onclick="slidePopup(-1)">❮</button>

    <div class="popup-content">
        <img id="popupImg" alt="Popup Image" style="display:none;">
        <video id="popupVideo" controls style="display:none;"></video>
    </div>

    <button type="button" class="popup-arrow popup-right" onclick="slidePopup(1)">❯</button>
</div>

<script>
setTimeout(function () {
    var msg = document.querySelector(".success-msg");
    if (msg) {
        msg.style.display = "none";
    }
}, 3000);
</script>
<script src="<%=request.getContextPath()%>/js/projectSlider.js"></script>
<script src="<%=request.getContextPath()%>/js/myProject.js"></script>
<script src="<%=request.getContextPath()%>/js/script.js"></script>
</body>
</html>