package com.peerreview.servlet;

import java.io.File;
import java.io.IOException;

import com.peerreview.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/uploadProfilePhoto")
@MultipartConfig
public class UploadProfilePhotoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIR = "C:/peerreview_uploads/profile";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("userEmail");

        Part filePart = request.getPart("profilePhoto");

        if (filePart != null && filePart.getSize() > 0) {
            File folder = new File(UPLOAD_DIR);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String originalFileName = filePart.getSubmittedFileName();
            String fileName = System.currentTimeMillis() + "_" + originalFileName;

            filePart.write(UPLOAD_DIR + File.separator + fileName);

            UserDAO dao = new UserDAO();
            dao.updateProfilePhoto(email, fileName);
        }

        response.sendRedirect("profile");
    }
}