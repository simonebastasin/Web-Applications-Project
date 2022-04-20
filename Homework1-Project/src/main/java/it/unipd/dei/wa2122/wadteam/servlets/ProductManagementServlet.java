package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.employee.DeleteEmployeeDatabase;
import it.unipd.dei.wa2122.wadteam.dao.media.ListMediaDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.*;
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
            case "editProduct" -> getEditProduct(req,res,param);
            case "deleteProduct" -> getDeleteProduct(req,res,param);
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
            case "editProduct" -> postEditProduct(req,res,param);
            case "deleteProduct" -> postDeleteProduct(req,res,param);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }

    }

    private void getListProduct(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        List<Product> products;

        try{
            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            List<Resource> lists = new ArrayList<>();
            for(var prod : products){
                if (prod.getQuantity() > 0)
                    lists.add(prod);
            }

            writeResource(req, res, "/jsp/productManagement.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            logger.error(e.getMessage());
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
        List<ProductCategory> categories;
        List<Media> media;
        List<Resource> resourcesList = new ArrayList<>();

       try{
            categories = new ListProductCategoryDatabase(getDataSource().getConnection()).getProductCategory();
            media = new ListMediaDatabase(getDataSource().getConnection()).getMedia();
            resourcesList.addAll(categories);
            resourcesList.addAll(media);
            writeResource(req, res, "/jsp/createProduct.jsp", false, resourcesList.toArray(Resource[]::new));

        }catch (SQLException e) {
           logger.error(e.getMessage());
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
     * get the jsp page editProduct.jsp for modifying an existing product
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void getEditProduct(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        Product product;
        List<Media> media;
        List<Resource> resourcesList = new ArrayList<>();

        try {
            product = new GetProductDatabase(getDataSource().getConnection(), param).getProduct();
            media = new ListMediaDatabase(getDataSource().getConnection()).getMedia();

            resourcesList.add(product);
            resourcesList.addAll(media);

            if(product != null) writeResource(req, res, "/jsp/editProduct.jsp", true, resourcesList.toArray(Resource[]::new));
            else writeError(req,res,GenericError.PAGE_NOT_FOUND);

        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }

    }

    /**
     * get the jsp page deleteProduct.jsp for deleting an existing product
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void getDeleteProduct(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        Product product;
        try {
            product = new GetProductDatabase(getDataSource().getConnection(), param).getProduct();

            if(product != null) writeResource(req, res, "/jsp/deleteProduct.jsp", true, product);
            else writeError(req,res,GenericError.PAGE_NOT_FOUND);

        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }

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
        boolean evidence = req.getParameter("evidence").equals("yes");
        //pictures management:
        String[] mediaStringArray = req.getParameterValues("media");   //returns an array of strings to be casted
        List<Integer> pictures = null;
        if(mediaStringArray != null){
            pictures = new ArrayList<>();
            for (String id: mediaStringArray) {
                pictures.add(Integer.parseInt(id));
            }
        }

        Product temp = new Product(alias,name,brand,description,quantity,purchase,sale,category,evidence,pictures, null);

        try {
            Product product = new CreateProductDatabase(getDataSource().getConnection(), temp).createProduct();
            //writeResource(req, res, "/jsp/productDetail.jsp", true , product); //view result
            Message m = new Message("edit ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") +"/management/productManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
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
            Message m = new Message("edit ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") +"/management/productManagement/createProduct");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }

    }


    /**
     * edit an existing product in the database
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void postEditProduct(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {
        String alias = param;
        String name = req.getParameter("name");
        String brand = req.getParameter("brand");
        String description = req.getParameter("description");
        double purchase = Double.parseDouble(req.getParameter("purchase"));
        double sale = Double.parseDouble(req.getParameter("sale"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        ProductCategory category = new ProductCategory(req.getParameter("category"),req.getParameter("category"));
        boolean evidence = req.getParameter("evidence").equals("yes");
        //pictures management:
        String[] mediaStringArray = req.getParameterValues("media");   //returns an array of strings to be casted
        List<Integer> pictures = null;
        if(mediaStringArray != null){
            pictures = new ArrayList<>();
            for (String id: mediaStringArray) {
                pictures.add(Integer.parseInt(id));
            }
        }

        Product temp = new Product(alias,name,brand,description,quantity,purchase,sale,category,evidence,pictures, null);

        try {
            int edit = new UpdateProductDatabase(getDataSource().getConnection(), temp).updateProduct();
            //writeResource(req, res, "/jsp/productDetail.jsp", true , product); //view result
            Message m = new Message("edit ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") +"/management/productManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * "delete" selected product from the database by simply setting its quantity to 0
     * @param req
     * @param res
     * @param param     alias of selected product to delete
     * @throws ServletException,
            * @throws IOException
     */
    private void postDeleteProduct(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        Product product;
        try {
            //set quantity = 0
            int affectedRows = new UpdateProductQuantityByAliasDatabase((getDataSource().getConnection()), param,0).updateProductQuantity();
            Message m = new Message("delete product ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") +"/management/productManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

}
