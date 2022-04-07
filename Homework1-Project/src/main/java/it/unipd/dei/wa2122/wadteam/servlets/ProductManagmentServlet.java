package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.product.ListProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.productCategory.ListProductCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.resources.Message;
import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;
import it.unipd.dei.wa2122.wadteam.resources.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductManagmentServlet extends AbstractDatabaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Product> products = null;

        try{
            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();


            List<Resource> lists = new ArrayList<>();
            for(var prod : products){
                    lists.add(prod);
            }

            writeResource(req, res, "/jsp/ProductManagment.jsp", false, lists.toArray(Resource[]::new));

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
