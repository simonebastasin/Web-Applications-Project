package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.discount.GetDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.discount.ListDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.owns.ListProductsFromIdDiscoutDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.CreateProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.ListProductDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountManagementServlet extends AbstractDatabaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "" -> getListDiscount(req, res);
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    private void getListDiscount(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<Discount> discounts = null;

        try{
            discounts = new ListDiscountDatabase(getDataSource().getConnection()).getDiscounts();


            List<Resource> lists = new ArrayList<>();
            for(var dis : discounts){

                List<Product> products = new ListProductsFromIdDiscoutDatabase(getDataSource().getConnection(), dis).getListProductsFromIdDiscoutDatabase();
                DiscountListProduct DiscountListProduct = new DiscountListProduct(dis, products);

                lists.add(DiscountListProduct);
            }

            writeResource(req, res, "/jsp/discountManagement.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));

        }
    }
}

