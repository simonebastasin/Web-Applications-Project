package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.OnlineOrder;
import it.unipd.dei.wa2122.wadteam.resources.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetProductsByOrderDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "select o.id, p.name, p.product_alias, c.price_applied, c.quantity from online_order as o " +
            "inner join contains as c on o.id = c.id_order " +
            "inner join product as p on p.product_alias=c.product_alias " +
            "where o.id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the onlineOrder
     */
    private final Integer id;

    /**
     * Creates a new object for getting an onlineOrder.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the id of the onlineOrder.
     */
    public GetProductsByOrderDatabase(Connection con, Integer id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Gets the products associated to the onlineOrder from the database.
     *
     * @return a list of {@code Product} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while deleting the onlineOrder.
     */
    public List<Product> getProductsByOrder() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Product> resultProducts = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                resultProducts.add(new Product(
                        rs.getString("product_alias"),
                        rs.getString("name"),
                        null,
                        null,
                        rs.getInt("quantity"),
                        rs.getDouble("price_applied"),
                        0,
                        null,
                        false,
                        null
                ));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();

        }

        return resultProducts;
    }
}
