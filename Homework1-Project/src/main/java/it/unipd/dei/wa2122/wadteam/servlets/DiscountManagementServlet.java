package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.discount.CreateDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.discount.DeleteDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.discount.GetDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.discount.ListDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.owns.CreateOwnsDiscountFromListProductsDatabase;
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
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }


    }

    private void getListDiscount(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<Discount> discounts;

        try{
            discounts = new ListDiscountDatabase(getDataSource().getConnection()).getDiscounts();


            List<Resource> lists = new ArrayList<>();
            for(var dis : discounts){

                List<Product> products = new ListProductsFromIdDiscoutDatabase(getDataSource().getConnection(), dis).getListProductsFromIdDiscoutDatabase();
                DiscountListProduct discountListProduct = new DiscountListProduct(dis, products);

                lists.add(discountListProduct);
            }

            writeResource(req, res, "/jsp/discountManagement.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));

        }
    }

    private void getCreateDiscount(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<Product> products;

        try{
            //TODO: Implement control in date input
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


        //StartDate
        int startDateday = Integer.parseInt(req.getParameter("startDateday"));
        int startDatemonth = Integer.parseInt(req.getParameter("startDatemonth"));
        int startDateyear = Integer.parseInt(req.getParameter("startDateyear"));


        DateTime startDate = new DateTime(LocalDateTime.of(startDateyear, startDatemonth, startDateday, 0,0,0));
        //EndDate
        int endDateday = Integer.parseInt(req.getParameter("endDateday"));
        int endDatemonth = Integer.parseInt(req.getParameter("endDatemonth"));
        int endDateyear = Integer.parseInt(req.getParameter("endDateyear"));

        DateTime endDate = new DateTime(LocalDateTime.of(endDateyear, endDatemonth, endDateday, 0,0,0));

        String[] productList = req.getParameterValues("productList");
        List<Product> productAliasList = new ArrayList<Product>();
        for(var pr : productList){
            productAliasList.add(new Product(pr, null, null, null, 0, 0.0, 0.0, null, false, null, null));
        }


        Discount temp = new Discount(55, percentage, startDate, endDate );


        try {
            Discount discount = new CreateDiscountDatabase(getDataSource().getConnection(), temp).createDiscount();

            List<Owns> list = new CreateOwnsDiscountFromListProductsDatabase(getDataSource().getConnection(), productAliasList, discount).createOwnsDiscountFromList();

            Message m = new Message("edit ok", discount.getId());
            writeMessageOrRedirect(req, res, m,  req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") + "/management/discountManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
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
            discount = new DeleteDiscountDatabase((getDataSource().getConnection()), Integer.parseInt(param)).deleteDiscount();

            Message m = new Message("delete ok");
            writeMessageOrRedirect(req, res, m, req.getContextPath() + (req.getServletPath().startsWith("/rest/") ? "/rest" : "") +"/management/discountManagement");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }
}


