package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.product.CreateProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.ListProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.productCategory.CreateProductCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.dao.productCategory.ListProductCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductManagementServlet extends AbstractDatabaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "" -> getListProduct(req, res);
            case "createProduct" -> getCreateProduct(req, res);
            case "createCategory" -> getCreateCategory(req,res);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "createProduct" -> postCreateProduct(req,res,param);
            case "createCategory" -> postCreateCategory(req,res,param);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }

    }

    private void getListProduct(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        List<Product> products = null;

        try{
            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            List<Resource> lists = new ArrayList<>();
            for(var prod : products){
                lists.add(prod);
            }

            writeResource(req, res, "/jsp/productManagement.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));

        }

    }

    /**
     * get the jsp page createProduct.jsp for creating a new product
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void getCreateProduct(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<ProductCategory> categories = null;

       try{
            categories = new ListProductCategoryDatabase(getDataSource().getConnection()).getProductCategory();
            writeResource(req, res, "/jsp/createProduct.jsp", false, categories.toArray(Resource[]::new));

        }catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));

        }
    }

    /**
     * get the jsp page createCategory.jsp for creating a new category
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void getCreateCategory(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        writeJsp(req,res,"/jsp/createCategory.jsp");
    }

    /**
     * creates a new product in the database
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void postCreateProduct(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        String alias = req.getParameter("alias");
        String name = req.getParameter("name");
        String brand = req.getParameter("brand");
        String description = req.getParameter("description");
        double purchase = Double.parseDouble(req.getParameter("purchase"));
        double sale = Double.parseDouble(req.getParameter("sale"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        ProductCategory category = new ProductCategory(req.getParameter("category"),req.getParameter("category"));
        boolean evidence = req.getParameter("evidence") == "yes"? true : false;;
        //private final List<Integer> pictures;   // todo

        Product temp = new Product(alias,name,brand,description,quantity,purchase,sale,category,evidence,null);

        try {
            Product product = new CreateProductDatabase(getDataSource().getConnection(), temp).createProduct();
            //writeResource(req, res, "/jsp/productDetail.jsp", true , product); //view result
            res.sendRedirect(req.getContextPath() + "/management/productManagement");
        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }

    }


    /**
     * creates a new category in the database
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void postCreateCategory(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        ProductCategory temp = new ProductCategory(name,description);

        try {
            ProductCategory resultProductCategory = new CreateProductCategoryDatabase(getDataSource().getConnection(), temp).createProductCategory();
            res.sendRedirect(req.getContextPath() + "/management/productManagement/createProduct");
        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }

    }

}
