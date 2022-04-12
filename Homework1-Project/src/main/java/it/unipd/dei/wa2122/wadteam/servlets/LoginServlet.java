package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.checkUser.CheckUserCredential;
import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
import it.unipd.dei.wa2122.wadteam.resources.GenericError;
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
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path){
            case "login" ->  {
                if(req.getSession(false) != null && req.getSession(false).getAttribute("users") != null) {
                    writeError(req, resp, GenericError.UNAUTHORIZED);
                } else {
                    writeJsp(req, resp, "/jsp/login.jsp");
                }
            }
            case "logout" -> {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/");
            }
            case "register" -> {
                if(req.getSession(false) != null && req.getSession(false).getAttribute("users") != null) {
                    writeError(req, resp, GenericError.UNAUTHORIZED);
                } else {
                    writeJsp(req,resp,"/jsp/register.jsp");
                }
            }
            default -> writeError(req, resp, GenericError.PAGE_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "login" -> {
                if(req.getSession(false) != null && req.getSession(false).getAttribute("users") != null) {
                    writeError(req, resp, GenericError.UNAUTHORIZED);
                }else {
                    String identification = req.getParameter("identification");
                    String password = req.getParameter("password");
                    TypeUser type = TypeUser.valueOf(req.getParameter("usertype").toUpperCase(Locale.ROOT));

                    UserCredential userCredentialAttempt = new UserCredential(identification, password, type, null, null, null);
                    try {
                        UserCredential userCredential = new CheckUserCredential(getDataSource().getConnection(), userCredentialAttempt).getUserCredentials();
                        if (userCredential != null && userCredential.getIdentification() != null) {
                            HttpSession session = req.getSession();
                            session.setAttribute("user", userCredential);

                            resp.sendRedirect(req.getContextPath() + "/");
                        } else {
                            writeError(req, resp, new ErrorMessage.UserCredentialError("Username or password aren't correct"));
                        }

                    } catch (SQLException e) {
                        writeError(req, resp, new ErrorMessage.SqlInternalError(e.getMessage()));

                    }
                }
            }
            default -> writeError(req, resp, GenericError.PAGE_NOT_FOUND);

        }
    }
}
