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
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {

        String[] paths = req.getPathInfo() != null ? req.getPathInfo().substring(1).split("/") : null;

        assert paths != null;
        String path = paths[0];

        switch (path){
            case "product" -> {
                String param = paths[1];
                buyProduct(req,res, param);
            }
            case "confirmed" -> {
                String param = paths[1];
                int selected = Integer.parseInt(paths[2]);
                confirmPayment(req,res, param, selected);
            }
        }
    }

    private void buyProduct(HttpServletRequest req, HttpServletResponse res, String param) throws  ServletException, IOException {

        Product product = null;

        try {
            product = new GetProductDatabase((getDataSource().getConnection()), param).getProduct();

            writeResource(req, res, "/jsp/buyProduct.jsp", true, product);

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void confirmPayment(HttpServletRequest req, HttpServletResponse res, String param, int selected) throws  ServletException, IOException {

        Product product = null;
        OnlineOrder newOrder = null;

        try {
            //product = new UpdateProductQuantityByAliasDatabase((getDataSource().getConnection()), param, selected).updateProductQuantity();

            product = new GetProductDatabase((getDataSource().getConnection()), param).getProduct();

            System.out.println("AOAOAOAOAOA");

            HttpSession session = req.getSession(false);
            int customerId = ((UserCredential) session.getAttribute("user")).getId();

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
                    new OrderStatus(1, OrderStatusEnum.OPEN, null, null, 1)
            );

            OnlineOrder processedOrder = new CreateOnlineOrderDatabase((getDataSource().getConnection()), newOrder).createOnlineOrder();

            List<Resource> resources = new ArrayList<>();
            resources.add(purchased);
            resources.add(processedOrder);

            writeResource(req, res, "/jsp/confirmedPayment.jsp", true, resources.toArray(Resource[]::new));

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }


    }
}
