package com.peerreview.servlet;

import java.io.IOException;

import com.peerreview.dao.ProjectDAO;
import com.peerreview.model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SubmitProjectServlet")
public class SubmitProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String github = request.getParameter("github");
		String email = request.getParameter("email");

		Project project = new Project();

		project.setTitle(title);
		project.setDescription(description);
		project.setGithubLink(github);
		project.setStudentEmail(email);

		ProjectDAO dao = new ProjectDAO();

		dao.addProject(project);

		response.sendRedirect("dashboard.html");
	}
}