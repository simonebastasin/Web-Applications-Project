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

public class ListOnlineInvoiceFromUserDatabase {
    private static final String STATEMENT = "SELECT oi.ID, oi.ID_Order,oi.Transaction_ID,oi.Payment_Type,oi.OI_Date,oi.Total_Price FROM " +
            "Online_Invoice as oi inner join " +
            "online_order as oo on oi.ID_Order = oo.ID where ID_Customer = ?";
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
    private final int id;

    /**
     * Creates a new object for reading an assistant ticket.
     *
     * @param con
     *            the connection to the database.
     */
    public ListOnlineInvoiceFromUserDatabase(final Connection con, int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Reads an asssistant ticket from the database.
     *
     * @return the {@code AssistantTicket} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the assistant ticket.
     */


    public List<OnlineInvoice> getOnlineInvoice() throws SQLException {
        ResultSet resultSet = null;
        List<OnlineInvoice> resultOnlineInvoice = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        PreparedStatement innerPreparedStatement = null;
        PreparedStatement pstmtProduct;
        ResultSet rsProduct;
        ResultSet innerResultSet = null;

        OrderStatus orderStatusResult;
        List<Product> products;

        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("ID");
                int idOrder = resultSet.getInt("ID_Order");
                String transactionId = resultSet.getString("Transaction_ID");
                PaymentMethodOnlineEnum paymentType = PaymentMethodOnlineEnum.fromString(resultSet.getString("Payment_Type"));
                DateTime date = new DateTime(resultSet.getObject("OI_Date", LocalDateTime.class));
                Double totalPrice=resultSet.getDouble("Total_Price");

                innerPreparedStatement = con.prepareStatement(STATEMENT_ORDER);
                innerPreparedStatement.setInt(1, idOrder);
                innerResultSet = innerPreparedStatement.executeQuery();

                GetOnlineOrderByIdDatabase order = null;
                while (innerResultSet.next()) {
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
                    resultOnlineInvoice.add(new OnlineInvoice(id, onlineOrder, transactionId, paymentType, date, totalPrice));
                    rsProduct.close();
                    pstmtProduct.close();
                }
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
