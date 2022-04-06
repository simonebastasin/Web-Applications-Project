package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserServlet extends AbstractDatabaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";
        switch (path) {
            case "info" -> writeJsp(req, resp, "/jsp/user.jsp");
            case "logout" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
            case "register" -> writeJsp(req, resp, "/jsp/user.jsp"); // TODO change
            default -> writeError(req, resp, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));

        }
    }
}
