<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.peerreview.model.Project" %>

<%
Project project = (Project) request.getAttribute("project");

if (project == null) {
    response.sendRedirect("myProjects");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Project</title>
<link rel="stylesheet" href="css/style.css">

<style>
body{
    background:#f1f5f9;
}

.edit-wrapper{
    max-width:720px;
    margin:120px auto 40px;
    background:#ffffff;
    border-radius:24px;
    padding:34px;
    box-shadow:0 14px 40px rgba(15,23,42,0.08);
    border:1px solid #e5e7eb;
}

.edit-wrapper h2{
    margin:0 0 24px;
    font-size:34px;
    font-weight:800;
    color:#0f172a;
    text-align:center;
}

.edit-form{
    display:flex;
    flex-direction:column;
    gap:16px;
}

.edit-form label{
    font-size:15px;
    font-weight:700;
    color:#334155;
    margin-bottom:6px;
    display:block;
}

.edit-form input,
.edit-form textarea{
    width:100%;
    padding:14px 16px;
    border:1px solid #dbe2ea;
    border-radius:14px;
    font-size:15px;
    outline:none;
    transition:0.3s ease;
    box-sizing:border-box;
}

.edit-form input:focus,
.edit-form textarea:focus{
    border-color:#2563eb;
    box-shadow:0 0 0 4px rgba(37,99,235,0.10);
}

.edit-form textarea{
    min-height:140px;
    resize:vertical;
}

.form-actions{
    display:flex;
    gap:14px;
    margin-top:10px;
}

.form-actions a,
.form-actions button{
    flex:1;
    height:52px;
    border:none;
    border-radius:14px;
    font-weight:700;
    font-size:16px;
    text-decoration:none;
    display:flex;
    align-items:center;
    justify-content:center;
    cursor:pointer;
    transition:0.3s ease;
}

.form-actions a{
    background:#e2e8f0;
    color:#0f172a;
}

.form-actions a:hover{
    background:#cbd5e1;
}

.form-actions button{
    background:#2563eb;
    color:#fff;
}

.form-actions button:hover{
    background:#1d4ed8;
}

.helper-text{
    font-size:13px;
    color:#64748b;
    margin-top:-6px;
}

@media (max-width:768px){
    .edit-wrapper{
        margin:100px 16px 30px;
        padding:22px;
    }

    .edit-wrapper h2{
        font-size:28px;
    }

    .form-actions{
        flex-direction:column;
    }
}
</style>
</head>
<body>

<nav class="navbar">
    <h2 class="logo">PeerReview</h2>
</nav>

<div class="edit-wrapper">
    <h2>Edit Project</h2>

    <form action="editProject" method="post" class="edit-form">
        <input type="hidden" name="id" value="<%= project.getId() %>">

        <div>
            <label>Project Title</label>
            <input type="text" name="title" value="<%= project.getTitle() %>" required>
        </div>

        <div>
            <label>Description</label>
            <textarea name="description" required><%= project.getDescription() %></textarea>
        </div>

        <div>
            <label>GitHub Project Link</label>
            <input type="url" name="githubLink"
                   value="<%= project.getGithubLink() != null ? project.getGithubLink() : "" %>"
                   placeholder="Enter GitHub project link (optional)">
            <div class="helper-text">Optional</div>
        </div>

        <div>
            <label>GitHub Profile Link</label>
            <input type="url" name="githubProfile"
                   value="<%= project.getGithubProfile() != null ? project.getGithubProfile() : "" %>"
                   placeholder="Enter GitHub profile link (optional)">
            <div class="helper-text">Optional</div>
        </div>

        <div class="form-actions">
            <a href="myProjects">Cancel</a>
            <button type="submit">Update Project</button>
        </div>
    </form>
</div>

</body>
</html>