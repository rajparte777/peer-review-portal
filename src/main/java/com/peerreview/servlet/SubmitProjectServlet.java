package com.peerreview.servlet;

import java.io.File;
import java.io.IOException;

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
@MultipartConfig
public class SubmitProjectServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        String uploadPath = "C:/peerreview/uploads/";
    	String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        /* -------- FILE PARTS -------- */

        Part image1Part = request.getPart("image1");
        Part image2Part = request.getPart("image2");
        Part videoPart = request.getPart("video");

        String image1Name = null;
        String image2Name = null;
        String videoName = null;

        /* IMAGE 1 */
        if (image1Part != null && image1Part.getSize() > 0) {
            image1Name = image1Part.getSubmittedFileName();
            image1Part.write(uploadPath + File.separator + image1Name);
        }

        /* IMAGE 2 */
        if (image2Part != null && image2Part.getSize() > 0) {
            image2Name = image2Part.getSubmittedFileName();
            image2Part.write(uploadPath + File.separator + image2Name);
        }

        /* VIDEO */
        if (videoPart != null && videoPart.getSize() > 0) {
            videoName = videoPart.getSubmittedFileName();
            videoPart.write(uploadPath + File.separator + videoName);
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

        project.setImage1(image1Name);
        project.setImage2(image2Name);
        project.setVideo(videoName);

        /* -------- SAVE -------- */

        ProjectDAO dao = new ProjectDAO();
        dao.addProject(project);

        response.sendRedirect("dashboard.html");
    }
}