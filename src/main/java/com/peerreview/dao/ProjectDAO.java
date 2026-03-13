package com.peerreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.peerreview.model.Project;
import com.peerreview.util.DBConnection;

public class ProjectDAO {

    // ADD PROJECT AND RETURN GENERATED ID
    public int addProject(Project project) {

        int projectId = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO projects(title, description, github_link, student_email) VALUES(?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, project.getTitle());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getGithubLink());
            ps.setString(4, project.getStudentEmail());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                projectId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectId;
    }

    // ADD MEDIA FILE
    public void addProjectMedia(int projectId, String fileName, String fileType) {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO project_media(project_id, file_name, media_type) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, projectId);
            ps.setString(2, fileName);
            ps.setString(3, fileType);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET ALL PROJECTS
    public List<Project> getAllProjects() {

        List<Project> projects = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM projects";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project p = new Project();

                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setDescription(rs.getString("description"));
                p.setGithubLink(rs.getString("github_link"));
                p.setStudentEmail(rs.getString("student_email"));

                // load media list
                p.setMediaList(getMediaByProjectId(p.getId()));

                projects.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }

    // GET MEDIA BY PROJECT ID
    public List<String[]> getMediaByProjectId(int projectId) {

        List<String[]> mediaList = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT file_name, media_type FROM project_media WHERE project_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, projectId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] media = new String[2];
                media[0] = rs.getString("file_name");
                media[1] = rs.getString("media_type");
                mediaList.add(media);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaList;
    }
}