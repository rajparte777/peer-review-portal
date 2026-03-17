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
<body>

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
        <form action="likeProject" method="post" style="display:inline;">
            <input type="hidden" name="projectId" value="<%= p.getId() %>">
            <button type="submit">
                <%= likedByUser ? "💔 Unlike" : "👍 Like" %> (<%= likeCount %>)
            </button>
        </form>
    </div>

    <div class="project-section">
        <h3>Comments (<%= commentCount %>)</h3>

        <form action="addComment" method="post">
            <input type="hidden" name="projectId" value="<%= p.getId() %>">
            <textarea name="comment" placeholder="Write your comment..." required></textarea>
            <button type="submit">💬 Add Comment</button>
        </form>

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
            <p>No comments yet.</p>
        <%
        }
        %>
    </div>

    <div class="project-section">
        <h3>Reviews (<%= reviewCount %>) | Average Rating: <%= String.format("%.1f", avgRating) %> / 5</h3>

        <form action="addReview" method="post">
            <input type="hidden" name="projectId" value="<%= p.getId() %>">

            <label>Rating:</label>
            <select name="rating" required>
                <option value="">Select</option>
                <option value="1">1 Star</option>
                <option value="2">2 Stars</option>
                <option value="3">3 Stars</option>
                <option value="4">4 Stars</option>
                <option value="5">5 Stars</option>
            </select>

            <textarea name="reviewText" placeholder="Write your review..." required></textarea>
            <button type="submit">⭐ Submit Review</button>
        </form>

        <%
        if(reviews != null && !reviews.isEmpty()){
            for(Review r : reviews){
        %>
            <div class="review-box">
                <p><b><%= r.getReviewerEmail() %></b> - <%= r.getRating() %>/5</p>
                <p><%= r.getReviewText() %></p>
            </div>
        <%
            }
        } else {
        %>
            <p>No reviews yet.</p>
        <%
        }
        %>
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
</body>
</html>