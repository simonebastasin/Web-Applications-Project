package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.product.GetProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.productCategory.GetProductCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
import it.unipd.dei.wa2122.wadteam.resources.Product;
import it.unipd.dei.wa2122.wadteam.resources.ProductCategory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ProductCategoryDetailServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1) : "";

        ProductCategory category = null;

        try {
            category = new GetProductCategoryDatabase((getDataSource().getConnection()), path).getProductCategory();

            System.out.println(category.getName() + " " + category.getDescription());

            writeResource(req, res, "/jsp/categoryDetail.jsp", true, category);

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        this.doGet(req,res);
    }

}
