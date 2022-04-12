package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.DeleteOnlineOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetListOnlineOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        int param = 0;
        if (req.getParameter("orderToDelete") != null) {
            param = Integer.parseInt(req.getParameter("orderToDelete"));
        }
        switch (path) {
            case "" -> getOrderList(req,res);
            case "editOrder" -> getEditOrder(req, res);
            case "deleteOrder" -> getDeleteOrder(req, res, param);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String[] paths = req.getPathInfo() != null ? req.getPathInfo().substring(1).split("/") : null;
        String path = paths[0];
        int param = 0;
        if (req.getParameter("orderToDelete") != null) {
            param = Integer.parseInt(req.getParameter("orderToDelete"));
        }
        switch (path) {
            case "editOrder" -> postEditOrder(req, res,0);
            case "deleteOrder" -> postDeleteOrder(req, res, param);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }
    }

    /**
     * show the list of all the orders in the database
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    private void getOrderList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<OnlineOrder> orderList = null;
        try{
            orderList = new GetListOnlineOrderDatabase(getDataSource().getConnection()).getListOnlineOrder();
            System.out.println("--->" + orderList.size());
            List<OnlineOrder> lists = new ArrayList<OnlineOrder>();
            for(var order : orderList){
                lists.add(order);
            }
            writeResource(req, res, "/jsp/orderManagement.jsp", false, lists.toArray(Resource[]::new));
        } catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void getEditOrder(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // TODO
        writeJsp(req, res,"/jsp/orderManagement.jsp");
    }

    /**
     *
     * @param req
     * @param res
     * @param param
     * @throws IOException
     * @throws ServletException
     */
    private void postEditOrder(HttpServletRequest req, HttpServletResponse res, int param) throws IOException, ServletException {
        // TODO
        writeJsp(req, res,"/jsp/orderManagement.jsp");
    }

    /**
     * get deleteOrder.jsp page to confirm deletion of selected online order
     * @param req
     * @param res
     * @param param     username of selected employee to confirm deletion
     * @throws ServletException
     * @throws IOException
     */
    private void getDeleteOrder(HttpServletRequest req, HttpServletResponse res, int param) throws ServletException, IOException {
        OnlineOrder order = null;
        try {
            order = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), param).getOnlineOrderId();
            List<OnlineOrder> list = new ArrayList<>();
            list.add(order);
            writeResource(req, res, "/jsp/deleteOrder.jsp", false, list.toArray(Resource[]::new));
        } catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * delete selected online order from the database
     * @param req
     * @param res
     * @param param     id of selected online order to delete
     * @throws ServletException,
     * @throws IOException
     */
    private void postDeleteOrder(HttpServletRequest req, HttpServletResponse res, int param) throws  ServletException, IOException {
        int idOrder;
        try {
            idOrder = new DeleteOnlineOrderDatabase((getDataSource().getConnection()), param).deleteOnlineOrder();
            res.sendRedirect(req.getContextPath() + "/management/orderManagement");
        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }
}