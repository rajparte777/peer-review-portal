package com.peerreview.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.peerreview.dao.InteractionDAO;
import com.peerreview.model.Review;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addReview")
public class ReviewServlet extends HttpServlet {
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

        String reviewerEmail = (String) session.getAttribute("userEmail");
        String projectIdParam = request.getParameter("projectId");
        String ratingParam = request.getParameter("rating");
        String reviewText = request.getParameter("reviewText");

        if (projectIdParam == null || projectIdParam.trim().isEmpty()) {
            out.print("{\"status\":\"error\",\"message\":\"Invalid project id\"}");
            return;
        }

        if (ratingParam == null || ratingParam.trim().isEmpty()) {
            out.print("{\"status\":\"error\",\"message\":\"Rating is required\"}");
            return;
        }

        if (reviewText == null || reviewText.trim().isEmpty()) {
            out.print("{\"status\":\"error\",\"message\":\"Review text cannot be empty\"}");
            return;
        }

        try {
            int projectId = Integer.parseInt(projectIdParam);
            int rating = Integer.parseInt(ratingParam);

            InteractionDAO dao = new InteractionDAO();
            boolean status = dao.addOrUpdateReview(projectId, reviewerEmail, rating, reviewText);

            if (status) {
                int reviewCount = dao.getReviewCount(projectId);
                double avgRating = dao.getAverageRating(projectId);
                List<Review> reviews = dao.getReviewsByProjectId(projectId);

                StringBuilder json = new StringBuilder();
                json.append("{");
                json.append("\"status\":\"success\",");
                json.append("\"reviewCount\":").append(reviewCount).append(",");
                json.append("\"avgRating\":\"").append(String.format("%.1f", avgRating)).append("\",");
                json.append("\"reviews\":[");

                for (int i = 0; i < reviews.size(); i++) {
                    Review r = reviews.get(i);

                    String safeEmail = r.getReviewerEmail() == null ? "" :
                            r.getReviewerEmail().replace("\\", "\\\\").replace("\"", "\\\"");
                    String safeText = r.getReviewText() == null ? "" :
                            r.getReviewText().replace("\\", "\\\\")
                                             .replace("\"", "\\\"")
                                             .replace("\n", "\\n")
                                             .replace("\r", "");

                    json.append("{");
                    json.append("\"reviewerEmail\":\"").append(safeEmail).append("\",");
                    json.append("\"rating\":").append(r.getRating()).append(",");
                    json.append("\"reviewText\":\"").append(safeText).append("\"");
                    json.append("}");

                    if (i < reviews.size() - 1) {
                        json.append(",");
                    }
                }

                json.append("]}");
                out.print(json.toString());

            } else {
                out.print("{\"status\":\"error\",\"message\":\"Unable to submit review\"}");
            }

        } catch (NumberFormatException e) {
            out.print("{\"status\":\"error\",\"message\":\"Invalid number format\"}");
        }
    }
}