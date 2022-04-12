package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.discount.CreateDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.discount.GetDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.discount.ListDiscountDatabase;
import it.unipd.dei.wa2122.wadteam.dao.owns.CreateOwnsDiscountFromListProductsDatabase;
import it.unipd.dei.wa2122.wadteam.dao.owns.ListProductsFromIdDiscoutDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.CreateProductDatabase;
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
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String[] paths = req.getPathInfo() != null ? req.getPathInfo().substring(1).split("/") : null;
        String path = paths[0];

        switch (path) {
            case "createDiscount" -> postCreateDiscount(req,res);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
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

    private void getCreateDiscount(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<Product> products = null;

        try{
            products = new ListProductDatabase(getDataSource().getConnection()).getProduct();

            List<Resource> lists = new ArrayList<>();
            for(var prod : products){
                lists.add(prod);
            }

            writeResource(req, res, "/jsp/createDiscount.jsp", false, lists.toArray(Resource[]::new));

        }catch (SQLException e) {
            Message m = new Message("Couldn't execute the query", "EU01", e.getMessage());
            writeError(req, res, m, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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

        int percentage = Integer.valueOf(req.getParameter("percentage"));


        //StartDate
        int startDateday = Integer.valueOf(req.getParameter("startDateday"));
        int startDatemonth = Integer.valueOf(req.getParameter("startDatemonth"));
        int startDateyear = Integer.valueOf(req.getParameter("startDateyear"));


        DateTime startDate = new DateTime(LocalDateTime.of(startDateyear, startDatemonth, startDateday, 0,0,0));
        //StartDate
        Integer endDateday = Integer.valueOf(req.getParameter("endDateday"));
        Integer endDatemonth = Integer.valueOf(req.getParameter("endDatemonth"));
        Integer endDateyear = Integer.valueOf(req.getParameter("endDateyear"));

        DateTime endDate = new DateTime(LocalDateTime.of(startDateyear, startDatemonth, startDateday, 0,0,0));

        String[] productList = req.getParameterValues("productList");
        List<Product> productAliasList = new ArrayList<Product>();
        for(var pr : productList){
            productAliasList.add(new Product(pr, null, null, null, 0, 0.0, 0.0, null, false, null));
        }


        Discount temp = new Discount(55, percentage, startDate, endDate );




        try {
            Discount discount = new CreateDiscountDatabase(getDataSource().getConnection(), temp).createDiscount();

            new CreateOwnsDiscountFromListProductsDatabase(getDataSource().getConnection(), productAliasList, discount);

            //writeResource(req, res, "/jsp/productDetail.jsp", true , product); //view result
            res.sendRedirect(req.getContextPath() + "/management/discountManagement");
        } catch (SQLException e) {
            writeError(req, res, new Message("Error create ticket", "ET02", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}

