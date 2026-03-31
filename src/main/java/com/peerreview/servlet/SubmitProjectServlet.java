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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/SubmitProjectServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 50,
    maxRequestSize = 1024 * 1024 * 200
)
public class SubmitProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIR = "C:/peerreview_uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("userEmail");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String github = request.getParameter("githubLink");
        String githubProfile = request.getParameter("githubProfile");

        if (github != null && github.trim().isEmpty()) {
            github = null;
        }

        if (githubProfile != null && githubProfile.trim().isEmpty()) {
            githubProfile = null;
        }

        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        project.setGithubLink(github);
        project.setGithubProfile(githubProfile);
        project.setStudentEmail(email);

        ProjectDAO dao = new ProjectDAO();
        int projectId = dao.addProject(project);

        if (projectId > 0) {

            File uploadFolder = new File(UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            Collection<Part> parts = request.getParts();

            for (Part part : parts) {
                if ("mediaFiles".equals(part.getName()) && part.getSize() > 0) {

                    String originalFileName = getFileName(part);

                    if (originalFileName != null && !originalFileName.isEmpty()) {

                        String fileName = System.currentTimeMillis() + "_" + originalFileName;
                        String filePath = UPLOAD_DIR + File.separator + fileName;

                        part.write(filePath);

                        String contentType = part.getContentType();
                        String mediaType = "image";

                        if (contentType != null && contentType.startsWith("video")) {
                            mediaType = "video";
                        }

                        dao.addProjectMedia(projectId, fileName, mediaType);
                    }
                }
            }

            response.sendRedirect("dashboard.jsp");
        } else {
            response.getWriter().println("<h3>Project submission failed!</h3>");
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");

        if (contentDisp != null) {
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    return s.substring(s.indexOf("=") + 2, s.length() - 1);
                }
            }
        }

        return null;
    }
}