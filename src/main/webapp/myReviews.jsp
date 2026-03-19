<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.peerreview.model.Review"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Reviews</title>
<link rel="stylesheet" href="css/viewProject.css">
</head>

<body class="view-project-page">

<h1>Reviews on Your Projects</h1>

<%
List<Review> reviews = (List<Review>) request.getAttribute("reviews");

if(reviews != null && !reviews.isEmpty()){
    for(Review r : reviews){
%>

<div class="project-card">

  <h2> <%= r.getProjectTitle() %></h2>

    <div class="review-box">
        <p><b>Reviewer:</b> <%= r.getReviewerEmail() %></p>
        <p><b>Rating:</b> ⭐ <%= r.getRating() %>/5</p>
        <%
int rating = r.getRating();
for(int i=1; i<=5; i++){
    if(i <= rating){
%>⭐<%
    } else {
%>☆<%
    }
}
%>
    </div>

</div>

<%
    }
} else {
%>
    <h3 style="text-align:center;">No reviews yet on your projects.</h3>
<%
}
%>

</body>
</html>