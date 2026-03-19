package com.peerreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.peerreview.model.Comment;
import com.peerreview.model.Review;
import com.peerreview.util.DBConnection;

public class InteractionDAO {

    // ===================== LIKE =====================

    public boolean addLike(int projectId, String userEmail) {
        boolean status = false;

        String checkSql = "SELECT id FROM likes WHERE project_id = ? AND user_email = ?";
        String insertSql = "INSERT INTO likes(project_id, user_email) VALUES(?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, projectId);
            checkPs.setString(2, userEmail);

            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    return false; // already liked
                }
            }

            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                insertPs.setInt(1, projectId);
                insertPs.setString(2, userEmail);

                int row = insertPs.executeUpdate();
                if (row > 0) {
                    status = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean removeLike(int projectId, String userEmail) {
        boolean status = false;

        String sql = "DELETE FROM likes WHERE project_id = ? AND user_email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setString(2, userEmail);

            int row = ps.executeUpdate();
            if (row > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean hasUserLiked(int projectId, String userEmail) {
        boolean liked = false;

        String sql = "SELECT id FROM likes WHERE project_id = ? AND user_email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setString(2, userEmail);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    liked = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return liked;
    }

    public int getLikeCount(int projectId) {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM likes WHERE project_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    // ===================== COMMENT =====================

    public boolean addComment(int projectId, String userEmail, String comment) {
        boolean status = false;

        if (comment == null || comment.trim().isEmpty()) {
            return false;
        }

        String sql = "INSERT INTO comments(project_id, user_email, comment) VALUES(?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setString(2, userEmail);
            ps.setString(3, comment);

            int row = ps.executeUpdate();
            if (row > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public List<Comment> getCommentsByProjectId(int projectId) {
        List<Comment> comments = new ArrayList<>();

        String sql = "SELECT id, project_id, user_email, comment, created_at FROM comments WHERE project_id = ? ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment c = new Comment();
                    c.setId(rs.getInt("id"));
                    c.setProjectId(rs.getInt("project_id"));
                    c.setUserEmail(rs.getString("user_email"));
                    c.setComment(rs.getString("comment"));
                    c.setCreatedAt(rs.getString("created_at"));
                    comments.add(c);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }

    public int getCommentCount(int projectId) {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM comments WHERE project_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    // ===================== REVIEW =====================

    public boolean addOrUpdateReview(int projectId, String reviewerEmail, int rating, String reviewText) {
        boolean status = false;

        if (rating < 1 || rating > 5) {
            return false;
        }

        String checkSql = "SELECT id FROM reviews WHERE project_id = ? AND reviewer_email = ?";
        String updateSql = "UPDATE reviews SET rating = ?, review_text = ? WHERE project_id = ? AND reviewer_email = ?";
        String insertSql = "INSERT INTO reviews(project_id, reviewer_email, rating, review_text) VALUES(?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, projectId);
            checkPs.setString(2, reviewerEmail);

            try (ResultSet rs = checkPs.executeQuery()) {

                if (rs.next()) {
                    try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                        updatePs.setInt(1, rating);
                        updatePs.setString(2, reviewText);
                        updatePs.setInt(3, projectId);
                        updatePs.setString(4, reviewerEmail);

                        int row = updatePs.executeUpdate();
                        if (row > 0) {
                            status = true;
                        }
                    }

                } else {
                    try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                        insertPs.setInt(1, projectId);
                        insertPs.setString(2, reviewerEmail);
                        insertPs.setInt(3, rating);
                        insertPs.setString(4, reviewText);

                        int row = insertPs.executeUpdate();
                        if (row > 0) {
                            status = true;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public List<Review> getReviewsByProjectId(int projectId) {
        List<Review> reviews = new ArrayList<>();

        String sql = "SELECT id, project_id, reviewer_email, rating, review_text, created_at FROM reviews WHERE project_id = ? ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review();
                    r.setId(rs.getInt("id"));
                    r.setProjectId(rs.getInt("project_id"));
                    r.setReviewerEmail(rs.getString("reviewer_email"));
                    r.setRating(rs.getInt("rating"));
                    r.setReviewText(rs.getString("review_text"));
                    r.setCreatedAt(rs.getString("created_at"));
                    reviews.add(r);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public double getAverageRating(int projectId) {
        double avg = 0.0;

        String sql = "SELECT AVG(rating) FROM reviews WHERE project_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    avg = rs.getDouble(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return avg;
    }

    public int getReviewCount(int projectId) {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM reviews WHERE project_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}