package it.unipd.dei.wa2122.wadteam.dao.orderStatus;

import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DeleteOrderStatusDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM order_status WHERE id = ? RETURNING id, status, description, os_datetime, id_order";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the ticket status
     */
    private final int id;

    public DeleteOrderStatusDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * delete OrderStatus from database with matching id
     * @return the {@code OrderStatus} object matching the id, or null if no match has been found.
     *
     * @throws SQLException
     *             if any error occurs while deleting the OrderStatus.
     */
    public OrderStatus deleteOrderStatus() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the deleted employee
        OrderStatus resultOrderStatus = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultOrderStatus = new OrderStatus(
                        resultSet.getInt("id"),
                        OrderStatusEnum.fromString(resultSet.getString("status")),
                        resultSet.getString("description"),
                        new DateTime(resultSet.getObject("os_datetime", LocalDateTime.class)),
                        resultSet.getInt("id_order")
                );
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
