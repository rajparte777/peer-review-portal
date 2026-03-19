package com.peerreview.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import com.peerreview.dao.ReviewDAO;
import com.peerreview.model.Review;

@WebServlet("/myReviews")
public class MyReviewsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ReviewDAO dao = new ReviewDAO();
        List<Review> reviews = dao.getReviewsForUserProjects(email);

        request.setAttribute("reviews", reviews);

        RequestDispatcher rd = request.getRequestDispatcher("myReviews.jsp");
        rd.forward(request, response);
    }
}