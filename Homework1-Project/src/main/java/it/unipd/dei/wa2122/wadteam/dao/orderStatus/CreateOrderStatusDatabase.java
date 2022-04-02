package it.unipd.dei.wa2122.wadteam.dao.orderStatus;

import it.unipd.dei.wa2122.wadteam.resources.OrderStatus;
import it.unipd.dei.wa2122.wadteam.resources.OrderStatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateOrderStatusDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO order_status (status, description, os_datetime, id_order) VALUES (?, ?, ?, ?) RETURNING id, status, description, os_datetime, id_order";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The order status
     */
    private OrderStatus orderStatus;

    public CreateOrderStatusDatabase(final Connection con, final OrderStatus orderStatus) {
        this.con = con;
        this.orderStatus = orderStatus;
    }


    public OrderStatus createOrderStatus() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // the create orderStatus
        OrderStatus resultOrderStatus = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, orderStatus.getStatus().toString()); //get user-friendly enum text
            preparedStatement.setString(2, orderStatus.getDescription());
            preparedStatement.setString(3, orderStatus.getOsDateTime());
            preparedStatement.setInt(4, orderStatus.getIdOrder());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultOrderStatus = new OrderStatus(
                        resultSet.getInt("id"),
                        OrderStatusEnum.valueOf(resultSet.getString("status")),
                        resultSet.getString("description"),
                        resultSet.getString("os_datetime"),
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

        return resultOrderStatus;
    }


}
