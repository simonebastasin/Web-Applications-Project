package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.login.CheckUserCredential;
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
        writeResource(req, resp, "/jsp/login.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String identification = req.getParameter("identification");
        String password = req.getParameter("password");
        TypeUser type = TypeUser.valueOf(req.getParameter("usertype").toUpperCase(Locale.ROOT));

        UserCredential userCredentialAttempt = new UserCredential(identification, password, type, null, null);
        try {
            UserCredential userCredential = new CheckUserCredential(getDataSource().getConnection(), userCredentialAttempt).getUserCredentials();
            if (userCredential.getIdentification() != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user",userCredential);
                //writeResource(req, resp, "/jsp/user.jsp", userCredential);
                resp.sendRedirect(req.getContextPath()+"/");
            } else {
                writeError(req, resp, new Message("Error login", "EV01", "Username or password aren't correct"),HttpServletResponse.SC_UNAUTHORIZED);
            }

        } catch (SQLException e) {
            writeError(req, resp, new Message("Error login", "EV02", e.getMessage()),HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }
    }
}
