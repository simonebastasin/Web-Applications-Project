package it.unipd.dei.wa2122.wadteam.servlets;

import it.unipd.dei.wa2122.wadteam.dao.product.GetListProductByCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.GetProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.product.ListProductDatabase;
import it.unipd.dei.wa2122.wadteam.dao.productCategory.GetProductCategoryDatabase;
import it.unipd.dei.wa2122.wadteam.resources.ErrorMessage;
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

public class ProductCategoryDetailServlet extends AbstractDatabaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String path = req.getPathInfo() != null ? req.getPathInfo().substring(1) : "";

        ProductCategory category = null;
        List<Product> products = null;

        try {
            category = new GetProductCategoryDatabase((getDataSource().getConnection()), path).getProductCategory();
            products = new GetListProductByCategoryDatabase(getDataSource().getConnection(), path).getListProductByCategory();

            List<Resource> list = new ArrayList<>();
            list.add(category);
            list.addAll(products);

            writeResource(req, res, "/jsp/categoryDetail.jsp", false, list.toArray(products.toArray(Resource[]::new)));

        } catch (SQLException e) {
            writeError(req, res, new ErrorMessage.SqlInternalError(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        this.doGet(req,res);
    }

}
