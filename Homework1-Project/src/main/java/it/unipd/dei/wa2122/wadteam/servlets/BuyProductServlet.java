package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.CreateOnlineOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.GetProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.UpdateProductQuantityByAliasDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyProductServlet extends AbstractDatabaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doGet(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        int selected;

        switch (path){
            case "product" -> {
                String stringSelected = req.getParameter("quantity");
                if(stringSelected.chars().allMatch( Character::isDigit ) && !stringSelected.equals("")) {
                    selected = Integer.parseInt(stringSelected);
                    buyProduct(req,res, param, selected);
                } else {
                    writeError(req, res, new ErrorMessage.IncorrectlyFormattedDataError("quantity"));
                }
            }
            case "confirmed" -> {
                String stringSelected = req.getParameter("quantity");
                if(stringSelected.chars().allMatch( Character::isDigit ) && !stringSelected.equals("")) {
                    selected = Integer.parseInt(stringSelected);
                    confirmPayment(req,res, param, selected);
                } else {
                    writeError(req, res, new ErrorMessage.IncorrectlyFormattedDataError("quantity"));
                }
            }
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    private void buyProduct(HttpServletRequest req, HttpServletResponse res, String param, int selected) throws  ServletException, IOException {

        Product product = null;

        try {
            product = new GetProductDatabase((getDataSource().getConnection()), param).getProduct();

            if(selected > product.getQuantity() || selected < 1){
                writeError(req, res, new ErrorMessage.SqlInternalError("Product quantity out of bounds"));
            }
            else {
                writeResource(req, res, "/jsp/buyProduct.jsp", true, product);
            }

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void confirmPayment(HttpServletRequest req, HttpServletResponse res, String param, int selected) throws  ServletException, IOException {

        Product product = null;
        OnlineOrder newOrder = null;

        try {
            product = new GetProductDatabase((getDataSource().getConnection()), param).getProduct();

            if(selected > product.getQuantity() || selected < 1){
                writeError(req, res, new ErrorMessage.InternalError("Product quantity out of bounds"));
            }
            else if(req.getSession(false) != null && req.getSession(false).getAttribute(USER_ATTRIBUTE) != null){
                HttpSession session = req.getSession(false);
                int customerId = ((UserCredential) session.getAttribute(USER_ATTRIBUTE)).getId();

                Product purchased = new Product(
                        product.getAlias(),
                        product.getName(),
                        product.getBrand(),
                        product.getDescription(),
                        selected,
                        product.getPurchase(),
                        product.getSale(),
                        product.getCategory(),
                        product.getEvidence(),
                        product.getPictures()
                );

                List<Product> list = new ArrayList<>();
                list.add(purchased);

                newOrder = new OnlineOrder(
                        null,
                        customerId,
                        null,
                        list,
                        new OrderStatus(
                                1,
                                OrderStatusEnum.OPEN,
                                null,
                                null,
                                1)
                );

                OnlineOrder processedOrder = new CreateOnlineOrderDatabase((getDataSource().getConnection()), newOrder).createOnlineOrder();

                List<Resource> resources = new ArrayList<>();
                resources.add(purchased);
                resources.add(processedOrder);

                writeResource(req, res, "/jsp/confirmedPayment.jsp", false, resources.toArray(Resource[]::new));
            } else {
                writeError(req, res, GenericError.UNAUTHORIZED);
            }
        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }
}
