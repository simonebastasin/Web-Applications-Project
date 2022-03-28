package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.login.CheckUserCredential;
import it.unipd.dei.wa2122.wadteam.resources.UserCredentials;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends AbstractDatabaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String identification = req.getParameter("identification ");
        String password = req.getParameter("password");
        UserCredentials.TypeUser type = UserCredentials.TypeUser.valueOf(req.getParameter("usertype"));

        UserCredentials user = new UserCredentials(identification,password, type, null);
        try {
            CheckUserCredential temp = new CheckUserCredential(getDataSource().getConnection(), user);

        UserCredentials user_returned = temp.getUserCredentials();
        if(user_returned.getIdentification() != null){
            resp.getWriter().printf("login ok");
        }
        else{
            resp.getWriter().printf("login bad");
        }

        }
        catch (SQLException e) {
            resp.getWriter().printf("login bad");

        }
    }
}
