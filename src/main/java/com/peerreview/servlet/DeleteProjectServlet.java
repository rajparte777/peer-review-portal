package com.peerreview.servlet;

import java.io.IOException;

import com.peerreview.dao.ProjectDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/deleteProject")
public class DeleteProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("userEmail");
        int projectId = Integer.parseInt(request.getParameter("projectId"));

        ProjectDAO dao = new ProjectDAO();
        dao.deleteProject(projectId, email);

        response.sendRedirect("myProjects");
    }
}