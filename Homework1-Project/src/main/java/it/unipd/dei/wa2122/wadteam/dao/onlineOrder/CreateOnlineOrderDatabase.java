package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateOnlineOrderDatabase {
    /**
     * The SQL statement to be executed
     */

    private static final String STATEMENT = "INSERT INTO Online_Order (id_customer) VALUES (?) RETURNING id, oo_datetime, id_customer";

    private static final String STATEMENT_INSERT_PRODUCT = "INSERT INTO Contains (id_order, product_alias, quantity, price_applied) VALUES (?, ?, ?, ?) RETURNING product_alias, quantity, price_applied";

    private static final String STATEMENT_INSERT_ORDER_STATUS = "INSERT INTO order_Status (Status, Description, ID_Order) VALUES (?::orderstatus, ?, ?) RETURNING id, Status, Description, ID_Order, OS_Datetime";
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The onlineOrder to be updated in the database
     */
    private final OnlineOrder onlineOrder;

    /**
     * Creates a new object for update an employee.
     *
     * @param con
     *            the connection to the database.
     * @param onlineOrder
     *            the employee to be created in the database.
     */
    public CreateOnlineOrderDatabase(final Connection con, final OnlineOrder onlineOrder) {
        this.con = con;
        this.onlineOrder = onlineOrder;
    }

    /**
     * Creates an onlineOrder in the database.
     *
     * @return the {@code OnlineOrder} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the onlineOrder.
     */
    public OnlineOrder createOnlineOrder() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        PreparedStatement pstmt_order = null;
        ResultSet rs_order = null;

        PreparedStatement pstmt_product = null;
        ResultSet rs_product = null;

        OnlineOrder resultOnlineOrder = null;



        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, onlineOrder.getIdCustomer());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int idCustomer = rs.getInt("id_customer");
                DateTime ooDateTime = new DateTime(rs.getObject("oo_datetime", LocalDateTime.class));


                pstmt_order = con.prepareStatement(STATEMENT_INSERT_ORDER_STATUS);
                pstmt_order.setString(1, onlineOrder.getStatus().getStatus().getText());
                pstmt_order.setString(2, onlineOrder.getStatus().getDescription());
                pstmt_order.setInt(3,id);

                rs_order = pstmt_order.executeQuery();

                OrderStatus resultOrderStatus = new OrderStatus(rs_order.getInt("id"),
                        OrderStatusEnum.fromString(rs_order.getString("Status")),
                        rs_order.getString("description"),
                        new DateTime(rs_order.getObject("os_datetime", LocalDateTime.class)),
                        rs_order.getInt("id_order")
                        );

                List<Product> resultProductList = new ArrayList<>();

                for(var item : onlineOrder.getProducts()) {
                    pstmt_product = con.prepareStatement(STATEMENT_INSERT_PRODUCT);
                    pstmt_product.setInt(1, id);
                    pstmt_product.setString(2, item.getAlias());
                    pstmt_product.setInt(3, item.getQuantity());
                    pstmt_product.setDouble(4, item.getSale());

                    rs_product = pstmt_product.executeQuery();

                    Product resultProductItem = new Product(
                            rs_product.getString("product_alias"),
                            null,
                            null,
                            null,
                            rs_product.getInt("quantity"),
                            0.0,
                            rs_product.getInt("price_applied"),
                            null,
                            false,
                            null);

                    resultProductList.add(resultProductItem);
                }

                resultOnlineOrder = new OnlineOrder(
                        id,idCustomer, ooDateTime,
                        resultProductList, resultOrderStatus
                );
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if(rs_order != null) {
                rs_order.close();
            }

            if(rs_product != null) {
                rs_product.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (pstmt_order != null) {
                pstmt_order.close();
            }

            if (pstmt_product != null) {
                pstmt_product.close();
            }

            con.close();
        }

        return resultOnlineOrder;
    }
}
