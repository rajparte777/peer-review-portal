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

@WebServlet("/LikeServlet")
public class LikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

        if (projectIdParam == null || projectIdParam.trim().isEmpty()) {
            out.print("{\"status\":\"error\",\"message\":\"Invalid project id\"}");
            return;
        }

        try {
            int projectId = Integer.parseInt(projectIdParam);

            InteractionDAO dao = new InteractionDAO();

            boolean liked;
            boolean result;

            if (dao.hasUserLiked(projectId, userEmail)) {
                result = dao.removeLike(projectId, userEmail);
                liked = false;
            } else {
                result = dao.addLike(projectId, userEmail);
                liked = true;
            }

            int likeCount = dao.getLikeCount(projectId);

            if (result) {
                out.print("{\"status\":\"success\",\"liked\":" + liked + ",\"likeCount\":" + likeCount + "}");
            } else {
                out.print("{\"status\":\"error\",\"message\":\"Database update failed\"}");
            }

        } catch (NumberFormatException e) {
            out.print("{\"status\":\"error\",\"message\":\"Project id must be a number\"}");
        }
    }
}