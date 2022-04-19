package it.unipd.dei.wa2122.wadteam.dao.onlineInvoice;

import it.unipd.dei.wa2122.wadteam.dao.onlineOrder.GetOnlineOrderByIdDatabase;
import it.unipd.dei.wa2122.wadteam.resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class GetOnlineInvoice {


    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM Online_Invoice WHERE ID = ?";
    private static final String STATEMENT_ORDER = "SELECT s.id_order, o.oo_datetime, o.id_customer, s.status, s.description, s.os_datetime, s.id as id_Status " +
            "FROM Online_Order as o " +
            "LEFT JOIN Order_Status as s on o.id = s.id_order " +
            "WHERE s.id_order = ?";
    private static final String STATEMENT_GET_PRODUCT = "select o.id, p.name, p.product_alias, c.price_applied, c.quantity from online_order as o " +
            "inner join contains as c on o.id = c.id_order " +
            "inner join product as p on p.product_alias=c.product_alias " +
            "where o.id = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the assistant ticket
     */
    private final int id;

    /**
     * Creates a new object for reading an assistant ticket.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the id of the Online Invoice.
     */
    public GetOnlineInvoice(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Reads an assistant ticket from the database.
     *
     * @return the {@code AssistantTicket} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the assistant ticket.
     */
    public OnlineInvoice getOnlineInvoice() throws SQLException {

        PreparedStatement preparedStatement = null;
        PreparedStatement innerPreparedStatement = null;

        ResultSet resultSet = null;
        ResultSet innerResultSet = null;

        PreparedStatement pstmtProduct = null;
        ResultSet rsProduct = null;

        OrderStatus orderStatusResult = null;
        List<Product> products = null;

        // the read employee
        OnlineInvoice resultOnlineInvoice = null;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("ID");
                int idOrder = resultSet.getInt("ID_Order");
                String transactionId = resultSet.getString("Transaction_ID");
                PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.fromString(resultSet.getString("Payment_Type "));
                DateTime date = resultSet.getObject("OI_Date  ", DateTime.class);
                Double totalPrice = resultSet.getDouble("Total_Price");
                PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.fromString(resultSet.getString("Payment_Type"));
                DateTime date = new DateTime(resultSet.getObject("OI_Date", LocalDateTime.class));
                Double totalPrice=resultSet.getDouble("Total_Price");

                innerPreparedStatement = con.prepareStatement(STATEMENT_ORDER);
                innerPreparedStatement.setInt(1, idOrder);
                innerResultSet = innerPreparedStatement.executeQuery();

                GetOnlineOrderByIdDatabase order = null;

                if (innerResultSet.next()) {
                    orderStatusResult = new OrderStatus(innerResultSet.getInt("id_Status"),
                            OrderStatusEnum.fromString(innerResultSet.getString("status")),
                            innerResultSet.getString("description"),
                            new DateTime(innerResultSet.getObject("oo_datetime", LocalDateTime.class)),
                            innerResultSet.getInt("id_order"));

                    products = new ArrayList<>();

                    OnlineOrder onlineOrder = new OnlineOrder(
                            innerResultSet.getInt("id_order"),
                            innerResultSet.getInt("id_customer"),
                            new DateTime(innerResultSet.getObject("oo_datetime", LocalDateTime.class)),
                            products, orderStatusResult
                    );

                    pstmtProduct = con.prepareStatement(STATEMENT_GET_PRODUCT);
                    pstmtProduct.setInt(1, onlineOrder.getIdOrder());

                    rsProduct = pstmtProduct.executeQuery();
                    while (rsProduct.next()) {
                        products.add(new Product(
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
                order = new GetOnlineOrderByIdDatabase(con, innerResultSet.getInt("ID_Order"));

                resultOnlineInvoice = new OnlineInvoice(id, order.getOnlineOrderId(), transactionId, paymentType, date, totalPrice);
                rsProduct.close();
                pstmtProduct.close();
            }
                innerResultSet.close();
                innerPreparedStatement.close();

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        con.close();

        return resultOnlineInvoice;
    }
}
