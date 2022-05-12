package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.CheckIfCanBeCreatedOnlineInvoiceDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.CreateOnlineInvoiceDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.GetOnlineInvoiceFromOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.CreateOnlineOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.DeleteOnlineOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.UpdateOnlineOrderDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.GetProductDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuyProductServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path){

            case "pay" -> {
                if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
                    int orderId = Integer.parseInt(param);
                    OnlineOrder processedOrder;
                    try {
                        processedOrder = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), orderId).getOnlineOrder();
                        if(processedOrder != null)
                        {
                            if(new CheckIfCanBeCreatedOnlineInvoiceDatabase(getDataSource().getConnection(), orderId).checkIfCanBeCreatedOnlineInvoice()) {
                                logger.info("quantity ok");
                                writeResource(req,res,"/jsp/buyChart.jsp",true, processedOrder);
                            } else {
                                logger.error("out of stock");
                                writeError(req, res, new ErrorMessage.ProductOutOfStockError("out of stock"));
                            }
                        }
                        else
                            writeError(req, res, GenericError.PAGE_NOT_FOUND);
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }
                } else {
                    logger.error("IncorrectlyFormattedDataError");
                    writeError(req, res, new ErrorMessage.IncorrectlyFormattedDataError("order id"));
                }
            }
            case "receipt" -> {
                if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
                    int orderId = Integer.parseInt(param);
                    OnlineInvoice onlineInvoice;
                    OnlineOrder onlineOrder;
                    try {
                        onlineInvoice = new GetOnlineInvoiceFromOrderDatabase(getDataSource().getConnection(), orderId).getOnlineInvoice();
                        if(onlineInvoice != null)
                            writeResource(req,res,"/jsp/buyChart.jsp",true, onlineInvoice, onlineInvoice.getIdOrder());
                        else if(null != (onlineOrder = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), orderId).getOnlineOrder())) {
                            if(new CheckIfCanBeCreatedOnlineInvoiceDatabase(getDataSource().getConnection(), orderId).checkIfCanBeCreatedOnlineInvoice()) {
                                logger.info("quantity ok");
                                writeResource(req,res,"/jsp/buyChart.jsp",true, onlineOrder);
                            } else {
                                logger.error("out of stock");
                                writeError(req, res, new ErrorMessage.ProductOutOfStockError("out of stock"));
                            }
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }
                } else {
                    logger.error("Incorrectly Formatted Data Error");
                    writeError(req, res, new ErrorMessage.IncorrectlyFormattedDataError("order id"));
                }
            }
            case "cancel" -> {
                OnlineOrder order;
                if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
                    int intParam = Integer.parseInt(param);
                    try {
                        order = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), intParam).getOnlineOrder();
                        if(order.getStatus().getStatus() == OrderStatusEnum.OPEN || order.getStatus().getStatus() == OrderStatusEnum.PAYMENT_ACCEPTED) {
                            writeResource(req, res, "/jsp/deleteOrder.jsp", true, order);

                        } else {
                            writeError(req, res, new ErrorMessage.CancelOrderError("order cannot be deleted"));
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
                    }
                } else {
                    writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter must be a number"));
                }
            }
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path){
            case "product" -> {
                String stringSelected = req.getParameter("quantity");
                if (stringSelected.chars().allMatch(Character::isDigit) && !stringSelected.equals("")) {
                    int selected = Integer.parseInt(stringSelected);
                    buyProduct(req, res, param, selected);
                } else {
                    logger.info("Incorrectly Formatted Data Error");
                    writeError(req, res, new ErrorMessage.IncorrectlyFormattedDataError("quantity"));
                }
            }
            case "cart" -> {
                JSONObject cart = readJSON(req);
                buyChart(req, res, cart);
            }
            case "pay" -> {
                confirmPayment(req,res, param);
            }
            case "cancel" -> {
                postDeleteOrder(req, res, param);
            }
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    private void buyProduct(HttpServletRequest req, HttpServletResponse res, String alias, int quantity) throws  ServletException, IOException {

        Product product;
        OnlineOrder newOrder;

        try {
            product = new GetProductDatabase(getDataSource().getConnection(), alias).getProduct();

            if(quantity > product.getQuantity() || quantity < 1){
                writeError(req, res, new ErrorMessage.ProductOutOfStockError("Product quantity out of bounds"));
            } else if (req.getSession(false) != null && req.getSession(false).getAttribute(USER_ATTRIBUTE) != null) {

                HttpSession session = req.getSession(false);
                int customerId = ((UserCredential) session.getAttribute(USER_ATTRIBUTE)).getId();

                Product purchased = new Product(
                        product.getAlias(),
                        product.getName(),
                        product.getBrand(),
                        product.getDescription(),
                        quantity,
                        product.getPurchasePrice(),
                        product.getFinalSalePrice(),
                        product.getCategory(),
                        product.getEvidence(),
                        product.getPictures(),
                        product.getDiscount());

                List<Product> list = new ArrayList<>();
                list.add(purchased);

                newOrder = new OnlineOrder(
                        null,
                        customerId,
                        null,
                        list,
                        new OrderStatus(
                                null,
                                OrderStatusEnum.OPEN,
                                null,
                                null,
                                null));

                OnlineOrder processedOrder = new CreateOnlineOrderDatabase((getDataSource().getConnection()), newOrder).createOnlineOrder();
                Message m = new Message("Order created", processedOrder.getIdOrder());
                writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/buy/pay/" + processedOrder.getIdOrder());
            } else {
                writeError(req, res, GenericError.UNAUTHORIZED);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }

    }

    private void buyChart(HttpServletRequest req, HttpServletResponse res, JSONObject cart)   throws  ServletException, IOException {
        JSONArray products = cart.getJSONArray("cart");
        System.out.println(products.toString());
        try {
            List<Product> productList = new ArrayList<>();

            HttpSession session = req.getSession(false);
            int customerId = ((UserCredential) session.getAttribute(USER_ATTRIBUTE)).getId();
            for(var item : products) {
                if (item instanceof JSONObject) {
                    Product product = Product.fromJSON((JSONObject) item);

                    Product dbProduct = new GetProductDatabase((getDataSource().getConnection()), product.getAlias()).getProduct();
                    System.out.println(dbProduct.getAlias());
                    if(product.getQuantity() > dbProduct.getQuantity() || product.getQuantity() < 1) {
                        writeError(req, res, new ErrorMessage.InternalError("Product quantity out of bounds"));
                    } else {
                        Product finalProduct = new Product(
                                dbProduct.getAlias(),
                                dbProduct.getName(),
                                dbProduct.getBrand(),
                                dbProduct.getDescription(),
                                product.getQuantity(),
                                null,
                                dbProduct.getFinalSalePrice(),
                                dbProduct.getCategory(),
                                dbProduct.getEvidence(),
                                null, null );
                        System.out.println(finalProduct.getAlias());
                        productList.add(finalProduct);
                        System.out.println(productList.size());
                    }
                }  else {
                    writeError(req, res, new ErrorMessage.InternalError("Parsing error"));
                }
            }
            if(productList.size() >= 1) {

                OnlineOrder newOrder = new OnlineOrder(
                        null,
                        customerId,
                        null,
                        productList,
                        new OrderStatus(
                                null,
                                OrderStatusEnum.OPEN,
                                null,
                                null,
                                null)
                );

                OnlineOrder processedOrder = new CreateOnlineOrderDatabase((getDataSource().getConnection()), newOrder).createOnlineOrder();
                if(processedOrder != null) {
                    Message m = new Message("Order created", processedOrder.getIdOrder());
                    writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/buy/pay/" + processedOrder.getIdOrder());
                } else {
                    writeError(req, res, new ErrorMessage.InternalError("Error generated error"));
                }
            } else {
                System.out.println("nooo");
                writeError(req, res, new ErrorMessage.InternalError("nothing to buy"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void confirmPayment(HttpServletRequest req, HttpServletResponse res, String orderId) throws  ServletException, IOException {

        if(orderId.chars().allMatch(Character::isDigit) && !orderId.equals("")) {
            OnlineOrder newOrder;
            OnlineInvoice invoice;

            PaymentMethodOnlineEnum paymentMethodOnlineEnum = PaymentMethodOnlineEnum.valueOf(req.getParameter("payment"));

            try {
                newOrder = new GetOnlineOrderByIdDatabase((getDataSource().getConnection()), Integer.parseInt(orderId)).getOnlineOrder();
                if(newOrder != null && newOrder.getStatus().getStatus() == OrderStatusEnum.OPEN) {
                    if(new CheckIfCanBeCreatedOnlineInvoiceDatabase(getDataSource().getConnection(),  Integer.parseInt(orderId)).checkIfCanBeCreatedOnlineInvoice()) {
                        invoice = new CreateOnlineInvoiceDatabase(getDataSource().getConnection(), new OnlineInvoice(null, newOrder, UUID.randomUUID().toString().replace("-","").substring(0,30), paymentMethodOnlineEnum, null, newOrder.getTotalPrice())).createOnlineInvoice();

                        OnlineOrder processedOrder = new OnlineOrder(
                                newOrder.getIdOrder(),
                                newOrder.getIdCustomer(),
                                newOrder.getOoDateTime(),
                                newOrder.getProducts(),
                                new OrderStatus(
                                        null,
                                        OrderStatusEnum.PAYMENT_ACCEPTED,
                                        null,
                                        null,
                                        null)
                        );

                        int result = new UpdateOnlineOrderDatabase((getDataSource().getConnection()),processedOrder).updateOnlineOrder();

                        if(result == 1) {
                            writeResource(req,res,"/jsp/buyChart.jsp",true, processedOrder, invoice);
                        } else {
                            writeError(req, res, new ErrorMessage.InternalError("Error on update"));
                        }
                    } else {
                        writeError(req, res, new ErrorMessage.ProductOutOfStockError("Product quantity out of bounds"));
                    }
                } else {
                    writeError(req, res, new ErrorMessage.OrderNotFoundError("Order not found"));
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter must be a number"));
        }
    }

    private void postDeleteOrder(HttpServletRequest req, HttpServletResponse res, String param) throws  ServletException, IOException {
        if (param.chars().allMatch(Character::isDigit) && !param.equals("")) {
            try {
                int intParam = Integer.parseInt(param);
                var order = new GetOnlineOrderByIdDatabase(getDataSource().getConnection(), intParam).getOnlineOrder();
                if(order.getStatus().getStatus() == OrderStatusEnum.OPEN || order.getStatus().getStatus() == OrderStatusEnum.PAYMENT_ACCEPTED) {
                    new DeleteOnlineOrderDatabase(getDataSource().getConnection(), intParam).deleteOnlineOrder();
                    Message m = new Message("edit ok");
                    writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") +"/order/list");
                } else {
                    writeError(req, res, new ErrorMessage.CancelOrderError("order cannot be deleted"));
                }
                     } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        } else {
            writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("last path parameter must be a number"));
        }
    }
}
