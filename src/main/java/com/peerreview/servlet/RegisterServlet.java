package com.peerreview.servlet;

import java.io.IOException;

import com.peerreview.dao.UserDAO;
import com.peerreview.util.EmailUtility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        // Check duplicate email
        if (dao.isEmailExists(email)) {
            response.getWriter().println("<h3 style='color:red; text-align:center; margin-top:50px;'>Email already registered! <a href='register.html'>Try Again</a></h3>");
            return;
        }

        // Register user
        boolean status = dao.registerUser(name, email, password);

        if (status) {

            // Send email to admin
            String adminEmail = "parteraj2005@gmail.com";
            String subject = "New User Registered - Peer Review Portal";
            String message = "A new user has registered on Peer Review Portal.\n\n"
                    + "Name: " + name + "\n"
                    + "Email: " + email + "\n";

            EmailUtility.sendEmail(adminEmail, subject, message);

            response.sendRedirect("login.jsp");

        } else {
            response.getWriter().println("<h3 style='color:red; text-align:center; margin-top:50px;'>Registration failed! <a href='register.html'>Try Again</a></h3>");
        }
    }
}