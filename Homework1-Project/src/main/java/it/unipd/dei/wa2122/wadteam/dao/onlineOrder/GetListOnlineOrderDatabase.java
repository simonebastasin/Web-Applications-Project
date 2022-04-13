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
        OnlineOrder onlineOrderResult;
        OrderStatus orderStatusResult;
        List<Product> productsResult = new ArrayList<>(); // to fill

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

                onlineOrderResult = new OnlineOrder(
                        rs.getInt("id_order"),
                        rs.getInt("id_customer"),
                        new DateTime(rs.getObject("oo_datetime", LocalDateTime.class)),
                        productsResult,
                        orderStatusResult
                );
                orders.add(onlineOrderResult);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        con.close();

        return orders;
    }
}
