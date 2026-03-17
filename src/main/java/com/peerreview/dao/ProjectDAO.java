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

            int row = ps.executeUpdate();

            if (row > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    projectId = rs.getInt(1);
                }
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

            String sql = "SELECT * FROM projects ORDER BY id DESC";
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

    // GET PROJECTS OF LOGGED-IN USER
    public List<Project> getProjectsByEmail(String email) {
        List<Project> projects = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM projects WHERE student_email = ? ORDER BY id DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

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

    // GET OTHER USERS PROJECTS
    public List<Project> getOtherProjects(String email) {
        List<Project> projects = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM projects WHERE student_email <> ? ORDER BY id DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

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

    // GET SINGLE PROJECT BY ID
    public Project getProjectById(int projectId) {
        Project p = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM projects WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, projectId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Project();

                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setDescription(rs.getString("description"));
                p.setGithubLink(rs.getString("github_link"));
                p.setStudentEmail(rs.getString("student_email"));

                // load media list
                p.setMediaList(getMediaByProjectId(projectId));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }

    // UPDATE PROJECT
    public boolean updateProject(Project project) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE projects SET title = ?, description = ?, github_link = ? WHERE id = ? AND student_email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, project.getTitle());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getGithubLink());
            ps.setInt(4, project.getId());
            ps.setString(5, project.getStudentEmail());

            int row = ps.executeUpdate();

            if (row > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // DELETE PROJECT
    public boolean deleteProject(int projectId, String email) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM projects WHERE id = ? AND student_email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, projectId);
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