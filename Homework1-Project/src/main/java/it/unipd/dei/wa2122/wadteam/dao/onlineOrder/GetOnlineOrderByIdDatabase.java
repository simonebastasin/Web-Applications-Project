package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetOnlineOrderByIdDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT s.id_order, o.oo_datetime, o.id_customer, s.status, s.description, s.os_datetime, s.id as id_Status " +
            "FROM Online_Order as o " +
            "LEFT JOIN Order_Status as s on o.id = s.id_order " +
            "WHERE s.id_order = ?";

    private static final String STATEMENT_GET_PRODUCT = "select o.id, p.name, p.brand, p.product_alias, c.price_applied, c.quantity from online_order as o " +
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
    private final Integer idOrder;

    /**
     * Creates a new object for getting an onlineOrder.
     *
     * @param con
     *            the connection to the database.
     * @param idOrder
     *            the id of the order.
     */
    public GetOnlineOrderByIdDatabase(final Connection con, final Integer idOrder) {
        this.con = con;
        this.idOrder = idOrder;
    }

    /**
     * Gets an onlineOrder from the database.
     *
     * @return the {@code OnlineOrder} object matching the id of the customer.
     *
     * @throws SQLException
     *             if any error occurs while deleting the onlineOrder.
     */
    public OnlineOrder getOnlineOrderId() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PreparedStatement pstmtProduct = null;
        ResultSet rsProduct = null;

        OrderStatus orderStatusResult;
        List<Product> products;
        OnlineOrder order = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idOrder);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                orderStatusResult = new OrderStatus(rs.getInt("id_Status"),
                        OrderStatusEnum.fromString(rs.getString("status")),
                        rs.getString("description"),
                        new DateTime(rs.getObject("oo_datetime", LocalDateTime.class)),
                        rs.getInt("id_order"));

                products = new ArrayList<>();

                OnlineOrder onlineOrder = new OnlineOrder(
                        rs.getInt("id_order"),
                        rs.getInt("id_customer"),
                        new DateTime(rs.getObject("oo_datetime", LocalDateTime.class)),
                        products, orderStatusResult
                );

                order = onlineOrder;

                pstmtProduct = con.prepareStatement(STATEMENT_GET_PRODUCT);
                pstmtProduct.setInt(1,onlineOrder.getIdOrder());

                rsProduct = pstmtProduct.executeQuery();

                while(rsProduct.next()) {
                    products.add( new Product(
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

        return order;
    }
}
