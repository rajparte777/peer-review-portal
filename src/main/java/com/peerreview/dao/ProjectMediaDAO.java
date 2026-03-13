package com.peerreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.peerreview.model.ProjectMedia;
import com.peerreview.util.DBConnection;

public class ProjectMediaDAO {

    public void insertMedia(ProjectMedia media) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO project_media(project_id, file_name, file_type) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, media.getProjectId());
            ps.setString(2, media.getFileName());
            ps.setString(3, media.getFileType());

            ps.executeUpdate();

            System.out.println("Media inserted into project_media table");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProjectMedia> getMediaByProjectId(int projectId) {
        List<ProjectMedia> mediaList = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM project_media WHERE project_id = ? ORDER BY id ASC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, projectId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProjectMedia media = new ProjectMedia();

                media.setId(rs.getInt("id"));
                media.setProjectId(rs.getInt("project_id"));
                media.setFileName(rs.getString("file_name"));
                media.setFileType(rs.getString("file_type"));

                mediaList.add(media);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaList;
    }
}