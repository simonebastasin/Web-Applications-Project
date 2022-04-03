package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.OnlineOrder;
import it.unipd.dei.wa2122.wadteam.resources.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsServlet extends AbstractDatabaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        OnlineOrder order = null;
        String path = req.getParameter("ID");
        Integer id = null;
        if(path.chars().allMatch(Character::isDigit)){
           id = Integer.parseInt(path);
        }

        try{

            order = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), id).getOnlineOrderId();

            writeResource(req,res, "/jsp/orderDetails.jsp",true, order);

        } catch (SQLException e) {
            Message m = new Message("Couldn't find the order", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
    }
}
