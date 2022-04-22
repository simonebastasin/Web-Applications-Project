package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.DeleteOnlineOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetListOnlineOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.dao.orderStatus.UpdateOrderStatusDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class OrderManagementServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "" -> getOrderList(req,res);
            case "editOrder" -> getEditOrder(req, res, param);
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
        try {
            List<OnlineOrder> orderList = new GetListOnlineOrderDatabase(getDataSource().getConnection()).getListOnlineOrder();
            Collections.reverse(orderList);
            writeResource(req, res, "/jsp/orderManagement.jsp", false, orderList.toArray(Resource[]::new));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * get editOrder.jsp page to edit an existing online order
     * @param req
     * @param res
     * @param param       id of selected online order to confirm editing
     * @throws IOException
     * @throws ServletException
     */
    private void getEditOrder(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
            int intParam = Integer.parseInt(param);
            try {
                OnlineOrder order = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), intParam).getOnlineOrder();
                if(order != null) {
                    writeResource(req, res, "/jsp/editOrder.jsp", false, order);
                }
                else {
                    writeError(req,res,GenericError.PAGE_NOT_FOUND);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter must be a number"));
        }
    }

    /**
     * edit an existing online order to the database
     * @param req
     * @param res
     * @param param     id of selected online order to edit
     * @throws IOException
     * @throws ServletException
     */
    private void postEditOrder(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
            OrderStatusEnum status = OrderStatusEnum.valueOf(req.getParameter("status"));
            String description = req.getParameter("description");
            int intParam = Integer.parseInt(param);
            OrderStatus orderStatus = new OrderStatus(null, status, description, null, intParam);
            try {
                int idOrder = new UpdateOrderStatusDatabase((getDataSource().getConnection()), orderStatus).updateOrderStatus();
                logger.info("Edit completed successfully for order " + idOrder);
                Message m = new Message("edit order status ok");
                writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/orderManagement");
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter must be a number"));
        }
    }

    /**
     * get deleteOrder.jsp page to confirm deletion of selected online order
     * @param req
     * @param res
     * @param param     id of selected online order to confirm deletion
     * @throws ServletException
     * @throws IOException
     */
    private void getDeleteOrder(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
            int intParam = Integer.parseInt(param);
            try {
                OnlineOrder order = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), intParam).getOnlineOrder();
                writeResource(req, res, "/jsp/deleteOrder.jsp", true, order);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter must be a number"));
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
        if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
            int intParam = Integer.parseInt(param);
            try {
                int idOrder = new DeleteOnlineOrderDatabase((getDataSource().getConnection()), intParam).deleteOnlineOrder();
                logger.info("Delete completed successfully for order " + idOrder);
                Message m = new Message("delete order status ok");
                writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/orderManagement");
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter must be a number"));
        }
    }
}