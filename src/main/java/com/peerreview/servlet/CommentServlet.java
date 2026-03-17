package com.peerreview.servlet;

import java.io.IOException;

import com.peerreview.dao.InteractionDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addComment")
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String comment = request.getParameter("comment");

        InteractionDAO dao = new InteractionDAO();

        if (comment != null && !comment.trim().isEmpty()) {
            dao.addComment(projectId, userEmail, comment);
        }

        response.sendRedirect("viewProjects");
    }
}