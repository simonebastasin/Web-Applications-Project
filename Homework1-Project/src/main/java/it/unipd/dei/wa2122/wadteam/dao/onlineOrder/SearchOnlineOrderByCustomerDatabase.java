package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.OnlineOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchOnlineOrderByCustomerDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, oo_datetime, id_customer FROM Online_Order WHERE id_customer = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The id of the onlineOrder
     */
    private final Integer id_customer;

    /**
     * Creates a new object for getting an onlineOrder.
     *
     * @param con
     *            the connection to the database.
     * @param id_customer
     *            the id of the customer.
     */
    public SearchOnlineOrderByCustomerDatabase(final Connection con, final Integer id_customer) {
        this.con = con;
        this.id_customer = id_customer;
    }

    /**
     * Gets an onlineOrder from the database.
     *
     * @return the {@code OnlineOrder} object matching the id of the customer.
     *
     * @throws SQLException
     *             if any error occurs while deleting the onlineOrder.
     */
    public List<OnlineOrder> searchOnlineOrderByCustomer() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the deleted onlineOrder
        final List<OnlineOrder> orders = new ArrayList<OnlineOrder>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id_customer);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                orders.add(new OnlineOrder(
                        rs.getInt("id"),
                        rs.getInt("id_customer"),
                        rs.getString("oo_datetime")
                ));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        return orders;
    }
}
