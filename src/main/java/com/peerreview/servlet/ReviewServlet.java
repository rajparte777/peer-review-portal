package com.peerreview.servlet;

import java.io.IOException;

import com.peerreview.dao.InteractionDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addReview")
public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String reviewerEmail = (String) session.getAttribute("userEmail");
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String reviewText = request.getParameter("reviewText");

        InteractionDAO dao = new InteractionDAO();
        dao.addOrUpdateReview(projectId, reviewerEmail, rating, reviewText);

        response.sendRedirect("viewProjects");
    }
}