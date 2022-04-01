package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.OnlineOrder;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatus;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum;
import it.unipd.dei.wa2122.wadteam.resources.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateOnlineOrderDatabase {
    /**
     * The SQL statement to be executed
     */

    private static final String STATEMENT = "INSERT INTO Online_Order (id_customer) VALUES (?) RETURNING id, oo_datetime, id_customer";

    private static final String STATEMENT_INSERT_PRODUCT = "INSERT INTO Contains (id_order, product_alias, quantity, price_applied) VALUES (?, ?, ?, ?) RETURNING product_alias, quantity, price_applied";

    private static final String STATEMENT_INSERT_ORDER_STATUS = "INSERT INTO order_Status (Status, Description, ID_Order) VALUES (?, ?, ?) RETURNING id, Status, Description, ID_Order, OS_Datetime";
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
        PreparedStatement innerpstmt = null;
        ResultSet innerrs = null;
        ResultSet innerrs2 = null;

        OnlineOrder resultOnlineOrder = null;



        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, onlineOrder.getIdCustomer());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int idCustomer = rs.getInt("id_customer");
                String ooDateTime = rs.getString("oo_datetime");


                innerpstmt = con.prepareStatement(STATEMENT_INSERT_ORDER_STATUS);
                innerpstmt.setString(1, onlineOrder.getStatus().getStatus().getText());
                innerpstmt.setString(2, onlineOrder.getStatus().getDescription());
                innerpstmt.setInt(3,id);

                innerrs = innerpstmt.executeQuery();

                OrderStatus resultOrderStatus = new OrderStatus(innerrs.getInt("id"),
                        OrderStatusEnum.fromString(innerrs.getString("Status")),
                        innerrs.getString("description"),
                        innerrs.getInt("id_order"),
                        innerrs.getString("os_datetime")
                        );

                List<Product> resultProductList = new ArrayList<>();

                for(var item : onlineOrder.getProducts()) {
                    pstmt = con.prepareStatement(STATEMENT_INSERT_PRODUCT);
                    pstmt.setInt(1, id);
                    pstmt.setString(2, item.getAlias());
                    pstmt.setInt(3, item.getQuantity());
                    pstmt.setDouble(4, item.getSale());

                    innerrs2 = pstmt.executeQuery();

                    Product resultProductItem = new Product(innerrs2.getString("product_alias"),null,
                            null,null,
                            innerrs2.getInt("quantity"),0.0,
                            innerrs2.getInt("price_applied"), null,false,null);

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

            if(innerrs != null) {
                innerrs.close();
            }

            if(innerrs2 != null) {
                innerrs2.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }

        return resultOnlineOrder;
    }
}
