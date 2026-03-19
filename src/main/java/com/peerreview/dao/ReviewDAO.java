package com.peerreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.peerreview.model.Review;
import com.peerreview.util.DBConnection;

public class ReviewDAO {

    public List<Review> getReviewsForUserProjects(String email) {
        List<Review> list = new ArrayList<>();

        String sql = "SELECT r.*, p.title FROM reviews r " +
                     "JOIN projects p ON r.project_id = p.id " +
                     "WHERE p.student_email = ? ORDER BY r.id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review();

                    r.setId(rs.getInt("id"));
                    r.setProjectId(rs.getInt("project_id"));
                    r.setReviewerEmail(rs.getString("reviewer_email"));
                    r.setRating(rs.getInt("rating"));
                    r.setReviewText(rs.getString("review_text"));
                    r.setCreatedAt(rs.getString("created_at"));
                    r.setProjectTitle(rs.getString("title"));

                    list.add(r);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getTotalReviews() {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM reviews";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}