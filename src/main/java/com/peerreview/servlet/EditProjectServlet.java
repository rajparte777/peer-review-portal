package com.peerreview.servlet;

import java.io.IOException;

import com.peerreview.dao.ProjectDAO;
import com.peerreview.model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/editProject")
public class EditProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int projectId = Integer.parseInt(request.getParameter("id"));

        ProjectDAO dao = new ProjectDAO();
        Project project = dao.getProjectById(projectId);

        if (project == null) {
            response.sendRedirect("myProjects");
            return;
        }

        request.setAttribute("project", project);
        request.getRequestDispatcher("editProject.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("userEmail");

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String github = request.getParameter("github");

        Project p = new Project();
        p.setId(id);
        p.setTitle(title);
        p.setDescription(description);
        p.setGithubLink(github);
        p.setStudentEmail(email);

        ProjectDAO dao = new ProjectDAO();
        dao.updateProject(p);

        response.sendRedirect("myProjects");
    }
}