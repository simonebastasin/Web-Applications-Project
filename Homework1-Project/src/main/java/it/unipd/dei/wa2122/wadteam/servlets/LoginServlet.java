package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.checkUser.CheckUserCredential;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.UserCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

import static it.unipd.dei.wa2122.wadteam.resources.UserCredential.*;

public class LoginServlet extends AbstractDatabaseServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path){
            case "/user/login" -> writeJsp(req, resp, "/jsp/login.jsp");
            case "/user/logout" -> {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String identification = req.getParameter("identification");
        String password = req.getParameter("password");
        TypeUser type = TypeUser.valueOf(req.getParameter("usertype").toUpperCase(Locale.ROOT));

        UserCredential userCredentialAttempt = new UserCredential(identification, password, type, null, null, null);
        try {
            UserCredential userCredential = new CheckUserCredential(getDataSource().getConnection(), userCredentialAttempt).getUserCredentials();
            if (userCredential != null && userCredential.getIdentification() != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user",userCredential);

                System.out.println(userCredential);

                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                writeError(req, resp, new Message("Error login", "EV01", "Username or password aren't correct"),HttpServletResponse.SC_UNAUTHORIZED);
            }

        } catch (SQLException e) {
            writeError(req, resp, new Message("Error login", "EV02", e.getMessage()),HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }
    }
}
