package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.product.GetProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.UpdateProductQuantityByAliasDatabase;
import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
import it.unipd.dei.wa2122.wadteam.resources.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class BuyProductServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {

        System.out.println("Path info:" + req.getPathInfo());

        String[] paths = req.getPathInfo() != null ? req.getPathInfo().substring(1).split("/") : null;

        String path = paths[0];

        System.out.println("path " + path);

        switch (path){
            case "product" -> {
                //String param = req.getPathInfo().substring(req.getPathInfo().lastIndexOf("/") + 1);
                String param = paths[1];
                int selected = Integer.parseInt(req.getParameter("quantity"));
                buyProduct(req,res, param, selected);
            }
            case "confirmed" -> {
                //String param = path.substring(path.lastIndexOf("/" + 1));
                String param = paths[1];
                System.out.println("PAPAP " + param);
                //path = path.substring(0,path.lastIndexOf("/"));
                System.out.println("aaka " + path);
                //int selected = Integer.parseInt(req.getPathInfo().substring(req.getPathInfo().lastIndexOf("/")));
                int selected = Integer.parseInt(paths[2]);
                confirmPayment(req,res, param, selected);
            }
        }
    }

    private void buyProduct(HttpServletRequest req, HttpServletResponse res, String param, int selected) throws  ServletException, IOException {

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

        try {
            //product = new UpdateProductQuantityByAliasDatabase((getDataSource().getConnection()), param, selected).updateProductQuantity();

            product = new GetProductDatabase((getDataSource().getConnection()), param).getProduct();

            writeResource(req, res, "/jsp/confirmedPayment.jsp", true, product);

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }


    }
}
