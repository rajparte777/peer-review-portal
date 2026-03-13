package com.peerreview.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.peerreview.dao.ProjectDAO;
import com.peerreview.model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/SubmitProjectServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,   // 2MB
    maxFileSize = 1024 * 1024 * 50,        // 50MB per file
    maxRequestSize = 1024 * 1024 * 200     // 200MB total
)
public class SubmitProjectServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Absolute upload folder inside deployed app
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        /* -------- FORM DATA -------- */
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String github = request.getParameter("github");
        String email = request.getParameter("email");

        /* -------- PROJECT OBJECT -------- */
        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        project.setGithubLink(github);
        project.setStudentEmail(email);

        /* -------- SAVE PROJECT FIRST -------- */
        ProjectDAO dao = new ProjectDAO();
        int projectId = dao.addProject(project);   // must return generated project id

        System.out.println("Project Added with ID: " + projectId);

        /* -------- SAVE MULTIPLE FILES -------- */
        Collection<Part> parts = request.getParts();

        for (Part part : parts) {
            if ("mediaFiles".equals(part.getName()) && part.getSize() > 0) {
            	System.out.println("Part Name: " + part.getName() + " | File: " + part.getSubmittedFileName());
                String fileName = part.getSubmittedFileName();

                if (fileName != null && !fileName.trim().isEmpty()) {

                    String uniqueFileName = System.currentTimeMillis() + "_" + fileName.replaceAll("\\s+", "_");

                    part.write(uploadPath + File.separator + uniqueFileName);

                    String contentType = part.getContentType();
                    String mediaType = (contentType != null && contentType.startsWith("video")) ? "video" : "image";

                    dao.addProjectMedia(projectId, uniqueFileName, mediaType);

                    System.out.println("Saved: " + uniqueFileName + " | " + mediaType);
                }
            }
        }

        response.sendRedirect("dashboard.html");
    }
}