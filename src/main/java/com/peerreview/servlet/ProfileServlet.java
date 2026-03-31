package com.peerreview.servlet;

import java.io.IOException;
import java.util.List;

import com.peerreview.dao.ProjectDAO;
import com.peerreview.dao.UserDAO;
import com.peerreview.model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("userEmail");

        UserDAO userDAO = new UserDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        String name = userDAO.getUserNameByEmail(email);
        int projectCount = projectDAO.getProjectCountByEmail(email);
        int totalLikes = projectDAO.getTotalLikesByEmail(email);
        int totalReviews = projectDAO.getTotalReviewsByEmail(email);
        double avgRating = projectDAO.getAverageRatingByEmail(email);

        List<Project> projects = projectDAO.getProjectsByEmail(email);

        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("projectCount", projectCount);
        request.setAttribute("totalLikes", totalLikes);
        request.setAttribute("totalReviews", totalReviews);
        request.setAttribute("avgRating", avgRating);
        request.setAttribute("projects", projects);

        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}