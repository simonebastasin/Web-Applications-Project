package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByCustomerDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.GetProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.ListProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.productCategory.ListProductCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePageServlet extends AbstractDatabaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Product> products = null;
        List<ProductCategory> categories = null;

        try{
            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            categories = new ListProductCategoryDatabase(getDataSource().getConnection()).getProductCategory();

            List<Resource> lists = new ArrayList<>();
            lists.addAll(products);
            lists.addAll(categories);

            writeResource(req, res, "/jsp/HomePage.jsp", false, lists.toArray(categories.toArray(Resource[]::new)));

        }catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        this.doGet(req,res);
    }
}
