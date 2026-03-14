package com.peerreview.servlet;

import java.io.IOException;

import com.peerreview.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        boolean status = dao.loginUser(email, password);

        if (status) {
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", email);

            String userName = dao.getUserNameByEmail(email);
            session.setAttribute("userName", userName);

            response.sendRedirect("dashboard.html");
        } else {
            response.getWriter().println("<h3 style='color:red; text-align:center; margin-top:50px;'>Invalid Email or Password! <a href='login.html'>Try Again</a></h3>");
        }
    }
}