package it.unipd.dei.wa2122.wadteam.dao.orderStatus;

import it.unipd.dei.wa2122.wadteam.resources.Discount;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatus;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListOrderStatusDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, status, description, os_datetime, id_order FROM order_status";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for reading order status.
     *
     * @param con
     *            the connection to the database.
     */
    public ListOrderStatusDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Reads all order status from the database.
     *
     * @return a list of OrderStatus {@code List<OrderStatus>}
     *
     * @throws SQLException
     *             if any error occurs while reading the order status.
     */


    public List<OrderStatus> getOrderStauts() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read discount
        OrderStatus resultOrderStatusItem = null;

        List<OrderStatus> resultOrderStatus = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);

            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                resultOrderStatusItem = new OrderStatus(resultSet.getInt("id"),
                        OrderStatusEnum.valueOf(resultSet.getString("status")),
                        resultSet.getString("description"),
                        resultSet.getString("os_datetime"),
                        resultSet.getInt("id_order"));
                resultOrderStatus.add(resultOrderStatusItem);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return resultOrderStatus;
    }
}
