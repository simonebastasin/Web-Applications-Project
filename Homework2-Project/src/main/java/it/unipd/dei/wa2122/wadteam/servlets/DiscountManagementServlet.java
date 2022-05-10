package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.discount.*;
import it.unipd.dei.wa2122.wadteam.dao.owns.CreateOwnsDiscountFromListProductsDatabase;
import it.unipd.dei.wa2122.wadteam.dao.owns.DeleteOwnsFromDiscountIdDatabase;
import it.unipd.dei.wa2122.wadteam.dao.owns.ListProductsFromIdDiscoutDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.ListProductDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class DiscountManagementServlet extends AbstractDatabaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "" -> getListDiscount(req, res);
            case "createDiscount" -> getCreateDiscount(req, res);
            case "editDiscount" -> getEditDiscount(req, res, param);
            case "deleteDiscount" -> getDeleteEmployee(req, res, param);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "createDiscount" -> postCreateDiscount(req,res);
            case "deleteDiscount" -> postDeleteDiscount(req, res, param);
            case "editDiscount" -> postEditDiscount(req, res, param);


            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }


    }

    /**
     * get discountManagement.jsp page to list all the discounts of the database
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    private void getListDiscount(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        List<Discount> discounts;
        List<Product> products;
        List<DiscountListProduct> DiscountListProducts = new ArrayList<>();

        try{
            discounts = new ListDiscountDatabase(getDataSource().getConnection()).getDiscounts();


            List<Resource> lists = new ArrayList<>();
            for(var dis : discounts){

                List<Product> products2 = new ListProductsFromIdDiscoutDatabase(getDataSource().getConnection(), dis).getListProductsFromIdDiscoutDatabase();
                DiscountListProducts.add( new DiscountListProduct(dis, products2));


            }

            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            lists.addAll(DiscountListProducts);
            lists.addAll(products);


            writeResource(req, res, "/jsp/discountManagement.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));

        }
    }

    private void getCreateDiscount(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<Product> products;

        try{

            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            List<Resource> lists = new ArrayList<>();
            for(var prod : products){
                lists.add(prod);
            }

            writeResource(req, res, "/jsp/createDiscount.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }

    }

    /**
     * get editDioscount.jsp page to edit a discount of the database
     * @param req
     * @param res
     * @param param     id of selected discount to confirm deletion
     * @throws ServletException
     * @throws IOException
     */
    private void getEditDiscount(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException{
        Discount discount;
        List<Product> products;
        try {

            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            List<Resource> list = new ArrayList<>();
            for(var prod : products){
                list.add(prod);
            }


            discount = new GetDiscountDatabase(getDataSource().getConnection(), Integer.parseInt(param)).getDiscount();
            List<Product> productsDiscounted = new ListProductsFromIdDiscoutDatabase(getDataSource().getConnection(), discount).getListProductsFromIdDiscoutDatabase();
            DiscountListProduct discountListProduct = new DiscountListProduct(discount, productsDiscounted);
            list.add(discountListProduct);


            writeResource(req, res, "/jsp/editDiscount.jsp", true, list.toArray(products.toArray(Resource[]::new)));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }

    }

    /**
     * get deleteDiscount.jsp page to confirm deletion of selected discount
     * @param req
     * @param res
     * @param param     id of selected discount to confirm deletion
     * @throws ServletException
     * @throws IOException
     */
    private void getDeleteEmployee(HttpServletRequest req, HttpServletResponse res, String param) throws ServletException, IOException {
        Discount discount;
        try {
            discount = new GetDiscountDatabase(getDataSource().getConnection(), Integer.parseInt(param)).getDiscount();
            List<Product> products = new ListProductsFromIdDiscoutDatabase(getDataSource().getConnection(), discount).getListProductsFromIdDiscoutDatabase();
            DiscountListProduct discountListProduct = new DiscountListProduct(discount, products);


            writeResource(req, res, "/jsp/deleteDiscount.jsp", true, discountListProduct);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    /**
     * creates a new discount in the database
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void postCreateDiscount(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        int percentage = Integer.parseInt(req.getParameter("percentage"));

        String startDateString = req.getParameter("start");
        String[] date1 = startDateString.split("-");

        //StartDate
        int startDateday = Integer.parseInt(date1[2]);
        int startDatemonth = Integer.parseInt(date1[1]);
        int startDateyear = Integer.parseInt(date1[0]);



        DateTime startDate = new DateTime(LocalDateTime.of(startDateyear, startDatemonth, startDateday, 0,0,0));
        //EndDate
        String endDateString = req.getParameter("end");
        String[] date2 = endDateString.split("-");

        int endDateday = Integer.parseInt(date2[2]);
        int endDatemonth = Integer.parseInt(date2[1]);
        int endDateyear = Integer.parseInt(date2[0]);

        DateTime endDate = new DateTime(LocalDateTime.of(endDateyear, endDatemonth, endDateday, 0,0,0));

        if(startDate.compareTo0(endDate) > 1 ){
            writeError(req,res,new ErrorMessage.DatesTimelineError("error in date format"));
        }else {


            String[] productList = req.getParameterValues("productList");

            if(productList == null){
                writeError(req,res,new ErrorMessage.EmptyProductListError("product list is empty"));
            }else {
                List<Product> productAliasList = new ArrayList<Product>();
                for (var pr : productList) {
                    productAliasList.add(new Product(pr, null, null, null, 0, 0.0, 0.0, null, false, null, null));
                }


                Discount temp = new Discount(55, percentage, startDate, endDate);

                try {
                    Discount discount = new CreateDiscountDatabase(getDataSource().getConnection(), temp).createDiscount();

                    List<Owns> list = new CreateOwnsDiscountFromListProductsDatabase(getDataSource().getConnection(), productAliasList, discount).createOwnsDiscountFromList();

                    logger.info("Insert completed successfully for discount "+discount.toString());
                    Message m = new Message("create discount ok", discount.getId());
                    writeMessageOrRedirect(req, res, m,  req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/discountManagement");
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    writeError(req, res, new ErrorMessage.CreateDiscountError(e.getMessage()));
                }
            }
            
        }


    }

    /**
     * edit an already existing discount in the database
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void postEditDiscount(HttpServletRequest req, HttpServletResponse res, String param) throws IOException, ServletException {

        int id = Integer.parseInt(param);
        int percentage = Integer.parseInt(req.getParameter("percentage"));

        String startDateString = req.getParameter("start");
        String[] date1 = startDateString.split("-");

        //StartDate
        int startDateday = Integer.parseInt(date1[2]);
        int startDatemonth = Integer.parseInt(date1[1]);
        int startDateyear = Integer.parseInt(date1[0]);



        DateTime startDate = new DateTime(LocalDateTime.of(startDateyear, startDatemonth, startDateday, 0,0,0));
        //EndDate
        String endDateString = req.getParameter("end");
        String[] date2 = endDateString.split("-");

        int endDateday = Integer.parseInt(date2[2]);
        int endDatemonth = Integer.parseInt(date2[1]);
        int endDateyear = Integer.parseInt(date2[0]);

        DateTime endDate = new DateTime(LocalDateTime.of(endDateyear, endDatemonth, endDateday, 0,0,0));

        if(startDate.compareTo0(endDate) > 1 ){
            writeError(req,res,new ErrorMessage.DatesTimelineError("error in date format"));
        }else {


            String[] productList = req.getParameterValues("productList");

            if(productList == null){
                writeError(req,res,new ErrorMessage.EmptyProductListError("product list is empty"));
            }else {
                List<Product> productAliasList = new ArrayList<Product>();
                for (var pr : productList) {
                    productAliasList.add(new Product(pr, null, null, null, 0, 0.0, 0.0, null, false, null, null));
                }


                Discount temp = new Discount(id, percentage, startDate, endDate);

                try {
                    Discount discount = new UpdateDiscountDatabase(getDataSource().getConnection(), temp).updateDiscount();



                    //Delete from OWNS table
                    new DeleteOwnsFromDiscountIdDatabase(getDataSource().getConnection(), id).deleteOwn();

                    //Create new OWNS relationships
                    List<Owns> list = new CreateOwnsDiscountFromListProductsDatabase(getDataSource().getConnection(), productAliasList, discount).createOwnsDiscountFromList();


                    //List<Owns> list = new UpdateOwn(getDataSource().getConnection(), productAliasList, discount).createOwnsDiscountFromList();
                    logger.info("Edit completed successfully for discount "+discount.toString());
                    Message m = new Message("edit discount ok", discount.getId());
                    writeMessageOrRedirect(req, res, m,  req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/discountManagement");
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
                }
            }

        }


    }
    /**
     * delete selected discount from the database
     * @param req
     * @param res
     * @param param     id of selected discount to delete
     * @throws ServletException,
     * @throws IOException
     */
    private void postDeleteDiscount(HttpServletRequest req, HttpServletResponse res, String param) throws  ServletException, IOException {
        Discount discount;
        try {
            new DeleteOwnsFromDiscountIdDatabase(getDataSource().getConnection(), Integer.parseInt(param)).deleteOwn();
            discount = new DeleteDiscountDatabase((getDataSource().getConnection()), Integer.parseInt(param)).deleteDiscount();

            logger.info("Delete completed successfully for discount "+discount.toString());
            Message m = new Message("delete discount ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") +"/management/discountManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.DeleteDiscountError(e.getMessage()));
        }
    }
}


