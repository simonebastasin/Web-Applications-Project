package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.product.GetListProductByCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.GetProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.ListProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.SearchProductListDatabase;
import it.unipd.dei.wa2122.wadteam.dao.productCategory.GetProductCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.dao.productCategory.ListProductCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class HomePageServlet extends AbstractDatabaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        String query = req.getParameter("q");

        switch (path){
            case "" -> homePage(req,res);
            case "details" -> productDetail(req,res,param);
            case "category" -> productCategory(req,res,param);
            case "search" -> {
                if(query.isBlank() || query.isEmpty()){
                    homePage(req,res);
                }
                else productSearch(req,res,query);
            }
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    private void productSearch(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException{
        String partialAlias = null;
        List<Product> products = null;

        try {
            products = new SearchProductListDatabase(getDataSource().getConnection(), param).searchProductList();

            List<Resource> list = new ArrayList<>();
            //list.add(category);
            for(var prod : products){
                if (prod.getQuantity() > 0)
                    list.add(prod);
            }

            writeResource(req, res, "/jsp/searchProduct.jsp", false, list.toArray(products.toArray(Resource[]::new)));

        } catch (SQLException e) {
            System.out.println("lol");
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void homePage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        List<Product> products = null;
        List<ProductCategory> categories = null;

        try{
            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            categories = new ListProductCategoryDatabase(getDataSource().getConnection()).getProductCategory();

            List<Resource> lists = new ArrayList<>();
            for(var prod : products){
                if (prod.getQuantity() > 0)
                    lists.add(prod);
            }
            lists.addAll(categories);

            writeResource(req, res, "/jsp/HomePage.jsp", false, lists.toArray(categories.toArray(Resource[]::new)));

        }catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void productCategory(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException{

        ProductCategory category = null;
        List<Product> products = null;

        try {
            category = new GetProductCategoryDatabase((getDataSource().getConnection()), param).getProductCategory();
            products = new GetListProductByCategoryDatabase(getDataSource().getConnection(), param).getListProductByCategory();

            List<Resource> list = new ArrayList<>();
            list.add(category);
            for(var prod : products){
                if (prod.getQuantity() > 0)
                    list.add(prod);
            }

            writeResource(req, res, "/jsp/categoryDetail.jsp", false, list.toArray(products.toArray(Resource[]::new)));

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    private void productDetail(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException{

        Product product = null;

        try {
            product = new GetProductDatabase((getDataSource().getConnection()), param).getProduct();

            writeResource(req, res, "/jsp/productDetail.jsp", true, product);

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        this.doGet(req,res);
    }
}
