package com.peerreview.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            out.print("{\"status\":\"error\",\"message\":\"User not logged in\"}");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");
        String projectIdParam = request.getParameter("projectId");
        String comment = request.getParameter("comment");

        if (projectIdParam == null || projectIdParam.trim().isEmpty()) {
            out.print("{\"status\":\"error\",\"message\":\"Invalid project id\"}");
            return;
        }

        if (comment == null || comment.trim().isEmpty()) {
            out.print("{\"status\":\"error\",\"message\":\"Comment cannot be empty\"}");
            return;
        }

        try {
            int projectId = Integer.parseInt(projectIdParam);

            InteractionDAO dao = new InteractionDAO();
            boolean status = dao.addComment(projectId, userEmail, comment);

            if (status) {
                int commentCount = dao.getCommentCount(projectId);

                String safeComment = comment.replace("\\", "\\\\")
                                            .replace("\"", "\\\"")
                                            .replace("\n", "\\n")
                                            .replace("\r", "");

                String safeUserEmail = userEmail.replace("\\", "\\\\")
                                                .replace("\"", "\\\"");

                out.print("{\"status\":\"success\",\"userEmail\":\"" + safeUserEmail + "\",\"comment\":\"" + safeComment + "\",\"commentCount\":" + commentCount + "}");
            } else {
                out.print("{\"status\":\"error\",\"message\":\"Unable to add comment\"}");
            }

        } catch (NumberFormatException e) {
            out.print("{\"status\":\"error\",\"message\":\"Project id must be a number\"}");
        }
    }
}