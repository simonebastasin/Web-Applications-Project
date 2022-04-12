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
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "" -> getOrderList(req,res);
            case "editOrder" -> getEditOrder(req, res);
            case "deleteOrder" -> getDeleteOrder(req, res, param);
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "editOrder" -> postEditOrder(req, res, param);
            case "deleteOrder" -> postDeleteOrder(req, res, param);
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
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
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));

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
    private void postEditOrder(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
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
    private void getDeleteOrder(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        OnlineOrder order = null;
        if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
            int intParam = Integer.parseInt(param);
            try {
                order = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), intParam).getOnlineOrderId();
                List<OnlineOrder> list = new ArrayList<>();
                list.add(order);
                writeResource(req, res, "/jsp/deleteOrder.jsp", false, list.toArray(Resource[]::new));
            } catch (SQLException e) {
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("path aren't a number"));
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
    private void postDeleteOrder(HttpServletRequest req, HttpServletResponse res, String param) throws  ServletException, IOException {
        if(param.chars().allMatch( Character::isDigit ) && !param.equals("")) {
            try {
                int intParam = Integer.parseInt(param);
                var idOrder = new DeleteOnlineOrderDatabase((getDataSource().getConnection()), intParam).deleteOnlineOrder();
                res.sendRedirect(req.getContextPath() + "/management/orderManagement");
            } catch (SQLException e) {
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("path aren't a number"));
        }
    }
}