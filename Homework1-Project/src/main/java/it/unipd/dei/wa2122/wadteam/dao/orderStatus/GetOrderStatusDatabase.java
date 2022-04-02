package it.unipd.dei.wa2122.wadteam.dao.orderStatus;

import it.unipd.dei.wa2122.wadteam.resources.Discount;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatus;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetOrderStatusDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, status, description, os_datetime, id_order FROM order_status WHERE ID = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The badge of the discount
     */
    private final int id;

    /**
     * Creates a new object for reading a order status.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the username of the discount.
     */
    public GetOrderStatusDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Reads an order status from the database.
     *
     * @return the {@code OrderStatus} object matching the id.
     *
     * @throws SQLException
     *             if any error occurs while reading the discount.
     */
    public OrderStatus getOrderStatus() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read discount
        OrderStatus resultOrderStatus = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultOrderStatus = new OrderStatus(resultSet.getInt("id"),
                        OrderStatusEnum.valueOf(resultSet.getString("status")),
                        resultSet.getString("description"),
                        resultSet.getString("os_datetime"),
                        resultSet.getInt("id_order"));

            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        con.close();

        return resultOrderStatus;
    }
}
