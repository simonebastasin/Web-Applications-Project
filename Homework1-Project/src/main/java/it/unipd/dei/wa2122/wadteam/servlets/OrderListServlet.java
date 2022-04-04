package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.customer.GetIdCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.OnlineOrder;
import it.unipd.dei.wa2122.wadteam.resources.Resource;
import it.unipd.dei.wa2122.wadteam.resources.UserCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderListServlet extends AbstractDatabaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<OnlineOrder> list = null;
        HttpSession session = req.getSession(false);
        UserCredential user = (UserCredential) session.getAttribute("user");
        String identification = user.getIdentification();
        Integer id;

        try{
            id = new GetIdCustomerDatabase(getDataSource().getConnection(), identification).getIdCustomer().getId();
            list = new GetOnlineOrderByCustomerDatabase(getDataSource().getConnection(), id).getOnlineOrderByCustomer();
            List<Resource> resList = new ArrayList<>();
            resList.addAll(list);
            writeResource(req,res, "/jsp/orderList.jsp", resList.toArray(Resource[]::new));

        } catch (SQLException e) {
            Message m = new Message("Couldn't find the order", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        /*res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();

        out.printf("<!DOCTYPE html>%n");
        out.printf("<html lang=\"en\">%n");
        out.printf("<head>%n");
        out.printf("<meta charset=\"UTF-8\">%n");
        out.printf("<title>OrderList Servlet</title>%n");
        out.printf("</head>%n");

        out.printf("<body>%n");
        out.printf("<h1>OrderList Servlet prova</h1>%n");
        out.printf("<form name = \"user\" action = \"\" method=\"post\">%n");
        out.printf("<input type=\"text\" name =\"identification\" id=\"identification\">%n");
        out.printf("<label for = \"identification\">userID</label>%n");
        out.printf("<input type =\"submit\" value = \"login\">%n");
        out.printf("</form>%n");
        out.printf("</body>%n");
        out.printf("</html>%n");

        out.flush();
        out.close();*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {


    }
}
