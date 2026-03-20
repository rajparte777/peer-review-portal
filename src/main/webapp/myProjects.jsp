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
<link rel="stylesheet" href="css/viewProject.css">
</head>
<body class="view-project-page">

<h1 style="text-align:center">My Uploaded Projects</h1>

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

    <p class="github">
        <a href="<%= p.getGithubLink() %>" target="_blank">View GitHub Project</a>
    </p>

    <p><b>Submitted by:</b> <%= p.getStudentEmail() %></p>

    <div class="actions">
        <a href="editProject?id=<%= p.getId() %>" class="action-btn edit-btn">✏ Edit</a>

        <form action="deleteProject" method="post" style="display:inline;">
            <input type="hidden" name="projectId" value="<%= p.getId() %>">
            <button type="submit" class="action-btn delete-btn"
                    onclick="return confirm('Are you sure you want to delete this project?');">
                🗑 Delete
            </button>
        </form>
    </div>

    <div class="project-section">
    <h3>Project Feedback Summary</h3>
    <div class="actions feedback-actions">
    <span class="like-count">👍 <%= likeCount %> Likes</span>

    <button type="button" class="feedback-chip"
        onclick="openFeedbackSection('comments-<%= p.getId() %>')">
        💬 <%= commentCount %> Comments
    </button>

    <button type="button" class="feedback-chip"
        onclick="openFeedbackSection('reviews-<%= p.getId() %>')">
        ⭐ <%= reviewCount %> Reviews
    </button>

    <span class="like-count">📊 <%= String.format("%.1f", avgRating) %> / 5</span>
</div>
</div>

<div class="project-section collapsed" id="comments-<%= p.getId() %>">
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
<div class="project-section collapsed" id="reviews-<%= p.getId() %>">
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
    <span onclick="closePopup()">✖</span>
    <img id="popupImg">
</div>

<script src="<%=request.getContextPath()%>/js/projectSlider.js"></script>
<script>
setTimeout(function () {
    var msg = document.querySelector(".success-msg");
    if (msg) {
        msg.style.display = "none";
    }
}, 3000);
</script>
<script src="<%=request.getContextPath()%>/js/script.js"></script>
</body>
</html>