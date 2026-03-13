package com.peerreview.servlet;

import java.io.IOException;
import java.util.List;

import com.peerreview.dao.ProjectDAO;
import com.peerreview.model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewProjects")
public class ViewProjectsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProjectDAO dao = new ProjectDAO();
        List<Project> projects = dao.getAllProjects();

        request.setAttribute("projects", projects);
        request.getRequestDispatcher("viewProjects.jsp").forward(request, response);
    }
}