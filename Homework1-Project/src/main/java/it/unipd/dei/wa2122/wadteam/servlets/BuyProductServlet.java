package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineInvoice.CreateOnlineInvoiceDatabase;
import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.CreateOnlineOrderDatabase;
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
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        int selected;

        switch (path){
            case "product" -> {
                if(!"".equals(param)) {
                    String stringSelected = req.getParameter("quantity");
                    if (stringSelected.chars().allMatch(Character::isDigit) && !stringSelected.equals("")) {
                        selected = Integer.parseInt(stringSelected);
                        buyProduct(req, res, param, selected);
                    } else {
                        writeError(req, res, new ErrorMessage.IncorrectlyFormattedDataError("quantity"));
                    }
                } else {
                    JSONObject cart = readJSON(req);
                    buyChart(req, res, cart);

                }
            }
            case "confirmed" -> {
                if(!"".equals(param)) {
                        String stringSelected = req.getParameter("quantity");
                    if(stringSelected.chars().allMatch( Character::isDigit ) && !stringSelected.equals("")) {
                        selected = Integer.parseInt(stringSelected);
                        confirmPayment(req,res, param, selected);
                    } else {
                        writeError(req, res, new ErrorMessage.IncorrectlyFormattedDataError("quantity"));
                    }
                } else {
                    JSONObject cart = readJSON(req);
                    confirmChart(req, res, cart);

                }
            }
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    private void buyProduct(HttpServletRequest req, HttpServletResponse res, String alias, int quantity) throws  ServletException, IOException {

        Product product = null;

        try {
            product = new GetProductDatabase((getDataSource().getConnection()), alias).getProduct();

            if(quantity > product.getQuantity() || quantity < 1){
                writeError(req, res, new ErrorMessage.SqlInternalError("Product quantity out of bounds"));
            }
            else {
                writeResource(req, res, "/jsp/buyProduct.jsp", true, product);
            }

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void confirmPayment(HttpServletRequest req, HttpServletResponse res, String alias, int quantity) throws  ServletException, IOException {

        Product product = null;
        OnlineOrder newOrder = null;

        PaymentMethodOnlineEnum paymentMethodOnlineEnum = PaymentMethodOnlineEnum.fromString(req.getParameter("payment"));

        try {
            product = new GetProductDatabase((getDataSource().getConnection()), alias).getProduct();

            if(quantity > product.getQuantity() || quantity < 1){
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
                        quantity,
                        product.getPurchasePrice(),
                        product.getDiscountSale() != null ? product.getDiscountSale() : product.getSalePrice(),
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
                                OrderStatusEnum.PAYMENT_ACCEPTED,
                                null,
                                null,
                                null)
                );

                OnlineOrder processedOrder = new CreateOnlineOrderDatabase((getDataSource().getConnection()), newOrder).createOnlineOrder();

                OnlineInvoice invoice = new CreateOnlineInvoiceDatabase(getDataSource().getConnection(), new OnlineInvoice(null, processedOrder, UUID.randomUUID().toString().replace("-","").substring(0,30), paymentMethodOnlineEnum, null, processedOrder.getTotalPrice())).createOnlineInvoice();

                writeResource(req, res, "/jsp/confirmedPayment.jsp", false, purchased, processedOrder, invoice);
            } else {
                writeError(req, res, GenericError.UNAUTHORIZED);
            }
        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void buyChart(HttpServletRequest req, HttpServletResponse res, JSONObject cart)   throws  ServletException, IOException {
        JSONArray products = cart.getJSONArray("cart");


        try {
            List<Product> productList = new ArrayList<>();

            HttpSession session = req.getSession(false);
            int customerId = ((UserCredential) session.getAttribute(USER_ATTRIBUTE)).getId();
            for(var item : products) {
                if (item instanceof JSONObject) {
                    Product product = Product.fromJSON((JSONObject) item);
                    Product dbProduct = new GetProductDatabase((getDataSource().getConnection()), product.getAlias()).getProduct();
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
                                product.getFinalSalePrice(),
                                dbProduct.getCategory(),
                                dbProduct.getEvidence(),
                                null, null );

                        productList.add(finalProduct);
                    }
                }  else {
                    writeError(req, res, new ErrorMessage.InternalError("Parsing error"));
                }
            }
            if(productList.size() > 1) {

                writeResource(req, res, "/jsp/buyChart.jsp", false, productList.toArray(Resource[]::new));

            } else {
                writeError(req, res, new ErrorMessage.InternalError("nothing to buy"));

            }

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void confirmChart(HttpServletRequest req, HttpServletResponse res, JSONObject cart)  throws  ServletException, IOException  {
        JSONArray products = cart.getJSONArray("cart");
        PaymentMethodOnlineEnum paymentMethodOnlineEnum = PaymentMethodOnlineEnum.fromJSON(cart.getJSONObject("paymentMethodOnline"));

        try {
            if(req.getSession(false) != null && req.getSession(false).getAttribute(USER_ATTRIBUTE) != null) {
                List<Product> productList = new ArrayList<>();

                HttpSession session = req.getSession(false);
                int customerId = ((UserCredential) session.getAttribute(USER_ATTRIBUTE)).getId();
                for(var item : products) {
                    if (item instanceof JSONObject) {
                        Product product = Product.fromJSON((JSONObject) item);
                        Product dbProduct = new GetProductDatabase((getDataSource().getConnection()), product.getAlias()).getProduct();
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
                                    product.getFinalSalePrice(),
                                    dbProduct.getCategory(),
                                    dbProduct.getEvidence(),
                                    null, null );

                            productList.add(finalProduct);
                        }
                    }  else {
                        writeError(req, res, new ErrorMessage.InternalError("Parsing error"));
                    }
                }
                if(productList.size() > 1) {
                    OnlineOrder newOrder = new OnlineOrder(
                            null,
                            customerId,
                            null,
                            productList,
                            new OrderStatus(
                                    null,
                                    OrderStatusEnum.PAYMENT_ACCEPTED,
                                    null,
                                    null,
                                    null)
                    );

                    OnlineOrder processedOrder = new CreateOnlineOrderDatabase((getDataSource().getConnection()), newOrder).createOnlineOrder();

                    OnlineInvoice invoice = new CreateOnlineInvoiceDatabase(getDataSource().getConnection(), new OnlineInvoice(null, processedOrder, UUID.randomUUID().toString(), paymentMethodOnlineEnum, null, processedOrder.getTotalPrice())).createOnlineInvoice();

                    List<Resource> resources = new ArrayList<>(productList);
                    resources.add(processedOrder);
                    resources.add(invoice);

                    writeResource(req, res, "/jsp/confirmedPayment.jsp", false, resources.toArray(Resource[]::new));

                } else {
                    writeError(req, res, new ErrorMessage.InternalError("nothing to buy"));

                }
            } else {
                writeError(req, res, GenericError.UNAUTHORIZED);

            }
        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

}
