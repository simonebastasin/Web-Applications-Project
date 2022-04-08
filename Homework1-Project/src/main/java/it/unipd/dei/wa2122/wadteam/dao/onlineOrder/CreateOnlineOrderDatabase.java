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

        PreparedStatement pstmtOrder = null;
        ResultSet rsOrder = null;

        PreparedStatement pstmtProduct = null;
        ResultSet rsProduct = null;

        OnlineOrder resultOnlineOrder = null;



        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, onlineOrder.getIdCustomer());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int idCustomer = rs.getInt("id_customer");
                DateTime ooDateTime = new DateTime(rs.getObject("oo_datetime", LocalDateTime.class));


                pstmtOrder = con.prepareStatement(STATEMENT_INSERT_ORDER_STATUS);
                pstmtOrder.setString(1, onlineOrder.getStatus().getStatus().getText());
                pstmtOrder.setString(2, onlineOrder.getStatus().getDescription());
                pstmtOrder.setInt(3,id);

                rsOrder = pstmtOrder.executeQuery();

                OrderStatus resultOrderStatus = new OrderStatus(rsOrder.getInt("id"),
                        OrderStatusEnum.fromString(rsOrder.getString("Status")),
                        rsOrder.getString("description"),
                        new DateTime(rsOrder.getObject("os_datetime", LocalDateTime.class)),
                        rsOrder.getInt("id_order")
                        );

                List<Product> resultProductList = new ArrayList<>();

                for(var item : onlineOrder.getProducts()) {
                    pstmtProduct = con.prepareStatement(STATEMENT_INSERT_PRODUCT);
                    pstmtProduct.setInt(1, id);
                    pstmtProduct.setString(2, item.getAlias());
                    pstmtProduct.setInt(3, item.getQuantity());
                    pstmtProduct.setDouble(4, item.getSale());

                    rsProduct = pstmtProduct.executeQuery();

                    Product resultProductItem = new Product(
                            rsProduct.getString("product_alias"),
                            null,
                            null,
                            null,
                            rsProduct.getInt("quantity"),
                            0.0,
                            rsProduct.getInt("price_applied"),
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

            if(rsOrder != null) {
                rsOrder.close();
            }

            if(rsProduct != null) {
                rsProduct.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (pstmtOrder != null) {
                pstmtOrder.close();
            }

            if (pstmtProduct != null) {
                pstmtProduct.close();
            }
        }
        con.close();

        return resultOnlineOrder;
    }
}
