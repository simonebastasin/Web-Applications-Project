package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetListOnlineOrderDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT s.id_order, o.oo_datetime, o.id_customer, s.status, s.description, s.os_datetime, s.id as id_Status " +
            "FROM Online_Order as o " +
            "LEFT JOIN Order_Status as s on o.id = s.id_order";

    private static final String STATEMENT_GET_PRODUCT = "select o.id, p.name, p.brand, p.product_alias, c.price_applied, c.quantity from online_order as o " +
            "inner join contains as c on o.id = c.id_order " +
            "inner join product as p on p.product_alias = c.product_alias " +
            "where o.id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for getting an onlineOrder.
     *
     * @param con
     *            the connection to the database.
     */
    public GetListOnlineOrderDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Gets an onlineOrder from the database.
     *
     * @return the {@code OnlineOrder} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while deleting the onlineOrder.
     */
    public List<OnlineOrder> getListOnlineOrder() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PreparedStatement pstmtProduct = null;
        ResultSet rsProduct = null;

        OnlineOrder onlineOrderResult;
        OrderStatus orderStatusResult;
        List<Product> productsResult;

        List<OnlineOrder> orders = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(STATEMENT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                orderStatusResult = new OrderStatus(rs.getInt("id_Status"),
                        OrderStatusEnum.fromString(rs.getString("status")),
                        rs.getString("description"),
                        new DateTime(rs.getObject("os_datetime", LocalDateTime.class)),
                        rs.getInt("id_order"));

                productsResult = new ArrayList<>();

                onlineOrderResult = new OnlineOrder(
                        rs.getInt("id_order"),
                        rs.getInt("id_customer"),
                        new DateTime(rs.getObject("oo_datetime", LocalDateTime.class)),
                        productsResult,
                        orderStatusResult
                );

                orders.add(onlineOrderResult);

                pstmtProduct = con.prepareStatement(STATEMENT_GET_PRODUCT);
                pstmtProduct.setInt(1,onlineOrderResult.getIdOrder());
                rsProduct = pstmtProduct.executeQuery();

                while(rsProduct.next()) {
                    productsResult.add( new Product(
                            rsProduct.getString("product_alias"),
                            rsProduct.getString("name"),
                            rsProduct.getString("brand"),
                            null,
                            rsProduct.getInt("quantity"),
                            0.0,
                            rsProduct.getDouble("price_applied"),
                            null,
                            false,
                            null,
                            null));
                }
            }

        } finally {

            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            if (rsProduct != null) {
                rsProduct.close();
            }
            if (pstmtProduct != null) {
                pstmtProduct.close();
            }

        }
        con.close();

        return orders;
    }
}
