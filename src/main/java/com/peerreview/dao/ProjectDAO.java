package com.peerreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	
	
	/*2nd method  for view*/
	public List<Project> getAllProjects(){

	    List<Project> projects = new ArrayList<>();

	    try{

	        Connection conn = DBConnection.getConnection();

	        String sql = "SELECT * FROM projects";

	        PreparedStatement ps = conn.prepareStatement(sql);

	        ResultSet rs = ps.executeQuery();

	        while(rs.next()){

	            Project p = new Project();

	            p.setId(rs.getInt("id"));
	            p.setTitle(rs.getString("title"));
	            p.setDescription(rs.getString("description"));
	            p.setGithubLink(rs.getString("github_link"));
	            p.setStudentEmail(rs.getString("student_email"));

	            projects.add(p);
	        }

	    }catch(Exception e){
	        e.printStackTrace();
	    }

	    return projects;
	}
}