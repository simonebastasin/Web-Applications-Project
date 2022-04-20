package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetOnlineOrderByCustomerDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT s.id_order, o.oo_datetime, o.id_customer, s.status, s.description, s.os_datetime, s.id as id_Status " +
            "FROM Online_Order as o " +
            "LEFT JOIN Order_Status as s on o.id = s.id_order " +
            "WHERE id_customer = ?";

    private static final String STATEMENT_GET_PRODUCT = "select o.id, p.name, p.product_alias, c.price_applied, c.quantity from online_order as o " +
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
    private final Integer idCustomer;

    /**
     * Creates a new object for getting an onlineOrder.
     *
     * @param con
     *            the connection to the database.
     * @param idCustomer
     *            the id of the customer.
     */
    public GetOnlineOrderByCustomerDatabase(final Connection con, final Integer idCustomer) {
        this.con = con;
        this.idCustomer = idCustomer;
    }

    /**
     * Gets an onlineOrder from the database.
     *
     * @return the {@code OnlineOrder} object matching the id of the customer.
     *
     * @throws SQLException
     *             if any error occurs while deleting the onlineOrder.
     */
    public List<OnlineOrder> getOnlineOrderByCustomer() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PreparedStatement pstmtProduct = null;
        ResultSet rsProduct = null;

        OrderStatus orderStatusResult;
        List<Product> products;
        final List<OnlineOrder> orders = new ArrayList<OnlineOrder>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idCustomer);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                orderStatusResult = new OrderStatus(rs.getInt("id_Status"),
                        OrderStatusEnum.fromString(rs.getString("status")),
                        rs.getString("description"),
                        new DateTime(rs.getObject("os_datetime", LocalDateTime.class)),
                        rs.getInt("id_order"));

                products = new ArrayList<>();

                OnlineOrder onlineOrder = new OnlineOrder(
                        rs.getInt("id_order"),
                        rs.getInt("id_customer"),
                        new DateTime(rs.getObject("oo_datetime", LocalDateTime.class)),
                        products, orderStatusResult
                );

                orders.add(onlineOrder);

                pstmtProduct = con.prepareStatement(STATEMENT_GET_PRODUCT);
                pstmtProduct.setInt(1,onlineOrder.getIdOrder());

                rsProduct = pstmtProduct.executeQuery();
                while(rsProduct.next()) {
                    products.add( new Product(
                            rsProduct.getString("product_alias"),
                            rsProduct.getString("name"),
                            null,
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
