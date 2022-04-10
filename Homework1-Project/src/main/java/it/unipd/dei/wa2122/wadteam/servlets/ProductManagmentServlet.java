package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.product.ListProductDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;
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
        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(1,req.getPathInfo().lastIndexOf('/')) : req.getPathInfo().substring(1) : "";
        String param = req.getPathInfo() != null ? req.getPathInfo().substring(1).lastIndexOf('/') != -1 ? req.getPathInfo().substring(req.getPathInfo().lastIndexOf('/')+1) : "" : "";

        switch (path) {
            case "" -> getListProduct(req, res);
            case "CreateProduct" -> getCreateProduct(req, res);
            default -> writeError(req, res, new ErrorMessage.IncorrectlyFormattedPathError("page not found"));
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        this.doGet(req,res);
    }

    private void getListProduct(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();
        switch (ut) {
            case CUSTOMER -> writeError(req, res, new ErrorMessage.UserCredentialError("User credential error"));
            case EMPLOYEE -> {
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
        }
    }

    private void getCreateProduct(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        var ut = ((UserCredential) req.getSession(false).getAttribute("user")).getType();
        switch (ut) {
            case CUSTOMER -> writeError(req, res, new ErrorMessage.UserCredentialError("User credential error"));
            case EMPLOYEE -> {
                writeJsp(req,res,"/jsp/createProduct.jsp");
            }
        }
    }

}
