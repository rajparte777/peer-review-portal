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

    private ProjectDAO dao = new ProjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("myProjects");
            return;
        }

        int id = Integer.parseInt(idParam);
        Project project = dao.getProjectById(id);

        if (project == null) {
        	response.sendRedirect("myProjects?msg=updated");
            return;
        }

        request.setAttribute("project", project);
        request.getRequestDispatcher("editProject.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String githubLink = request.getParameter("githubLink");

        Project p = new Project();
        p.setId(id);
        p.setTitle(title);
        p.setDescription(description);
        p.setGithubLink(githubLink);
        p.setStudentEmail(userEmail);

        boolean status = dao.updateProject(p);

        if (status) {
            response.sendRedirect("myProjects?msg=updated");
        } else {
            response.getWriter().println("Project update failed");
        }
    }
}