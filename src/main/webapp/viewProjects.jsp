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
<title>View Projects</title>
<link rel="stylesheet" href="css/viewProject.css">
</head>
<body class="view-project-page">

<h1 style="text-align:center">Submitted Projects</h1>

<%
List<Project> projects = (List<Project>) request.getAttribute("projects");
InteractionDAO interactionDAO = new InteractionDAO();
String loggedInEmail = (String) session.getAttribute("userEmail");

if(projects != null && !projects.isEmpty()){
    for(Project p : projects){

        int likeCount = interactionDAO.getLikeCount(p.getId());
        int commentCount = interactionDAO.getCommentCount(p.getId());
        int reviewCount = interactionDAO.getReviewCount(p.getId());
        double avgRating = interactionDAO.getAverageRating(p.getId());

        boolean likedByUser = false;
        if(loggedInEmail != null){
            likedByUser = interactionDAO.hasUserLiked(p.getId(), loggedInEmail);
        }

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

    <p>
        <a href="<%= p.getGithubLink() %>" target="_blank">View GitHub Project</a>
    </p>

    <p>Submitted by: <%= p.getStudentEmail() %></p>

    <div class="actions">
        <button 
            type="button"
            class="like-btn <%= likedByUser ? "liked" : "" %>"
            data-project-id="<%= p.getId() %>"
            onclick="toggleLike(this)">
            <span class="like-text"><%= likedByUser ? "💔 Unlike" : "👍 Like" %></span>
        </button>

        <span class="like-count" id="like-count-<%= p.getId() %>"><%= likeCount %></span>
    </div>

    <!-- COMMENTS SECTION -->
    <div class="project-section">
        <h3>Comments (<span id="comment-count-<%= p.getId() %>"><%= commentCount %></span>)</h3>

        <form onsubmit="submitComment(event, this)">
            <input type="hidden" name="projectId" value="<%= p.getId() %>">
            <textarea name="comment" placeholder="Write your comment..." required></textarea>
            <button type="submit">💬 Add Comment</button>
        </form>

        <div class="comment-list" id="comment-list-<%= p.getId() %>">
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
                <p class="no-comment">No comments yet.</p>
            <%
            }
            %>
        </div>
    </div>

    <!-- REVIEWS SECTION -->
    <div class="project-section">
        <h3>
            Reviews (<span id="review-count-<%= p.getId() %>"><%= reviewCount %></span>) |
            Average Rating: <span id="avg-rating-<%= p.getId() %>"><%= String.format("%.1f", avgRating) %></span> / 5
        </h3>

       <form onsubmit="submitReview(event, this)">
    <input type="hidden" name="projectId" value="<%= p.getId() %>">

    <label>Rating:</label>

    <div class="star-rating">
        <span data-value="1">★</span>
        <span data-value="2">★</span>
        <span data-value="3">★</span>
        <span data-value="4">★</span>
        <span data-value="5">★</span>
    </div>

    <input type="hidden" name="rating" class="rating-value" required>

    <textarea name="reviewText" placeholder="Write your review..." required></textarea>
    <button type="submit">⭐ Submit Review</button>
</form>

        <div class="review-list" id="review-list-<%= p.getId() %>">
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
                <p class="no-review">No reviews yet.</p>
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
    <h3 style="text-align:center; margin-top:40px;">No projects found.</h3>
<%
}
%>

<div id="popup" class="popup">
    <span onclick="closePopup()">✖</span>
    <img id="popupImg">
</div>

<script src="<%=request.getContextPath()%>/js/projectSlider.js"></script>
<script src="<%=request.getContextPath()%>/js/likeProject.js"></script>
<script src="<%=request.getContextPath()%>/js/projectInteraction.js"></script>
</body>
</html>