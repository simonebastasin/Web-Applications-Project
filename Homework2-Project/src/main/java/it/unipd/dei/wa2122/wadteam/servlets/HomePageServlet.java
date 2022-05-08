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
import org.json.*;

import java.io.IOException;
import java.io.PrintWriter;
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
                    res.sendRedirect(req.getContextPath()+"/");
                }
                else productSearch(req,res,query);
            }
            case "suggest"->{
                productSuggest(req,res,query);
            }
            default -> writeError(req, res, GenericError.PAGE_NOT_FOUND);
        }
    }

    private void productSuggest(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        String partialAlias = null;
        List<Product> products;
       JSONArray jsonArray=new JSONArray();

        try {
            products = new SearchProductListDatabase(getDataSource().getConnection(), param).searchProductList();

            for (var prod : products) {
                if (prod.getQuantity() >0)
                {
                    jsonArray.put(new JSONObject().put("alias", prod.getAlias()).put("name",prod.getName()));
                }
            }
        }catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
        JSONObject jo=new JSONObject();
        jo.put("products",jsonArray);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(jo.toString());




    }

    private void productSearch(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException{
        String partialAlias = null;
        List<Product> products;

        try {
            products = new SearchProductListDatabase(getDataSource().getConnection(), param).searchProductList();
            List<Resource> list = new ArrayList<>();
            for(var prod : products){
                if (prod.getQuantity() > 0)
                    list.add(prod);
            }
            logger.info("A customer is using the search bar to look for: "+ param);
            writeResource(req, res, "/jsp/searchProduct.jsp", false, list.toArray(products.toArray(Resource[]::new)));

        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * get HomePage.jsp page
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    private void homePage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        List<Product> products;
        List<ProductCategory> categories;

        try{
            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            categories = new ListProductCategoryDatabase(getDataSource().getConnection()).getProductCategory();

            List<Resource> lists = new ArrayList<>();
            for(var prod : products){
                if (prod.getQuantity() > 0)
                    lists.add(prod);
            }
            lists.addAll(categories);

            logger.info("The homepage is loaded");
            writeResource(req, res, "/jsp/HomePage.jsp", false, lists.toArray(categories.toArray(Resource[]::new)));

        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * get categoryDetail.jsp page
     * @param req
     * @param res
     * @param param
     * @throws ServletException
     * @throws IOException
     */
    private void productCategory(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException{

        if(param.isEmpty()) {
            writeJsp(req,res,"/jsp/categoryList.jsp");
        }
        else{
            ProductCategory category;
            List<Product> products;

            try {
                category = new GetProductCategoryDatabase((getDataSource().getConnection()), param).getProductCategory();


                if(category != null){
                    products = new GetListProductByCategoryDatabase(getDataSource().getConnection(), param).getListProductByCategory();
                    List<Resource> list = new ArrayList<>();
                    list.add(category);
                    for(var prod : products){
                        if (prod.getQuantity() > 0)
                            list.add(prod);
                    }

                    logger.info("The category "+ category.getName() + " is loaded");
                    writeResource(req, res, "/jsp/categoryDetail.jsp", false, list.toArray(products.toArray(Resource[]::new)));
                }
                else writeError(req, res, GenericError.PAGE_NOT_FOUND);



            } catch (SQLException e) {
                logger.error(e.getMessage());
                writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
            }
        }

    }

    /**
     * get productDetail.jsp page
     * @param req
     * @param res
     * @param param
     * @throws ServletException
     * @throws IOException
     */
    private void productDetail(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException{

        Product product;

        try {
            product = new GetProductDatabase(getDataSource().getConnection(), param).getProduct();

            if(product != null){
                logger.info("Product "+ product.getName() +" is loaded");
                writeResource(req, res, "/jsp/productDetail.jsp", true, product);
            }
            else writeError(req, res, GenericError.PAGE_NOT_FOUND);


        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        this.doGet(req,res);
    }
}
