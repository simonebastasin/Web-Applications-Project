package it.unipd.dei.wa2122.wadteam.dao.orderStatus;

import it.unipd.dei.wa2122.wadteam.resources.DateTime;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatus;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UpdateOrderStatusDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE order_status SET status = ?::orderstatus, description = ?, os_datetime = ?, id_order = ? WHERE id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The item of the order status that will be updated
     */
    private final OrderStatus orderStatus;

    /**
     * Set up the database connection and define the order status to be updated
     *
     * @param con
     *            the connection to the database.
     * @param orderStatus
     *            the order status to be updated.
     */
    public UpdateOrderStatusDatabase(final Connection con, final OrderStatus orderStatus) {
        this.con = con;
        this.orderStatus = orderStatus;
    }

    /**
     * Update an order status corresponding to a given id.
     *
     * @return the new, modified, {@code OrderStatus} object.
     *
     * @throws SQLException
     *             if any error occurs while updating the order status.
     */
    public OrderStatus updateOrderStatus() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the read employee
        OrderStatus resultOrderStatus = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, orderStatus.getStatus().getText());
            preparedStatement.setString(2, orderStatus.getDescription());
            preparedStatement.setObject(3, orderStatus.getOsDateTime().getLocalDateTime());
            preparedStatement.setInt(4, orderStatus.getIdOrder());
            preparedStatement.setInt(5, orderStatus.getId());


            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultOrderStatus = new OrderStatus(
                        resultSet.getInt("id"),
                        OrderStatusEnum.fromString(resultSet.getString("status")),
                        resultSet.getString("description"),
                        new DateTime(resultSet.getObject("os_datetime", LocalDateTime.class)),
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
