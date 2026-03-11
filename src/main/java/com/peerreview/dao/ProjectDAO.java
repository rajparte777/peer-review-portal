package com.peerreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.peerreview.model.Project;
import com.peerreview.util.DBConnection;

public class ProjectDAO {

	public void addProject(Project project) {

		try {

			Connection conn = DBConnection.getConnection();

			String sql = "INSERT INTO projects(title,description,github_link,student_email) VALUES(?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, project.getTitle());
			ps.setString(2, project.getDescription());
			ps.setString(3, project.getGithubLink());
			ps.setString(4, project.getStudentEmail());

			ps.executeUpdate();

			System.out.println("Project Added");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}