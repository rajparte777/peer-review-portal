package com.peerreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.peerreview.util.DBConnection;

public class UserDAO {

    // ===================== CHECK EMAIL =====================
    public boolean isEmailExists(String email) {
        boolean exists = false;

        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    exists = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

    // ===================== REGISTER USER =====================
    public boolean registerUser(String name, String email, String password) {
        boolean status = false;

        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // ===================== LOGIN USER =====================
    public boolean loginUser(String email, String password) {
        boolean status = false;

        String sql = "SELECT 1 FROM users WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    status = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // ===================== GET USER NAME =====================
    public String getUserNameByEmail(String email) {
        String name = null;

        String sql = "SELECT name FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    // ===================== TOTAL USERS (DASHBOARD) =====================
    public int getTotalUsers() {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    // get img by email
    public String getProfilePhotoByEmail(String email) {
        String photo = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT profile_photo FROM users WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                photo = rs.getString("profile_photo");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return photo;
    }
    //update img
    public boolean updateProfilePhoto(String email, String fileName) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE users SET profile_photo = ? WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fileName);
            ps.setString(2, email);

            int row = ps.executeUpdate();

            if (row > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}