package it.unipd.dei.wa2122.wadteam.dao.onlineOrder;

import it.unipd.dei.wa2122.wadteam.resources.Employee;
import it.unipd.dei.wa2122.wadteam.resources.OnlineOrder;
import it.unipd.dei.wa2122.wadteam.resources.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateOnlineOrderDatabase {
    /**
     * The SQL statement to be executed
     */

    private static final String STATEMENT = "INSERT INTO Online_Order (id, oo_datetime, id_customer) VALUES (?, ?, ?) RETURNING id, oo_datetime, id_customer";

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

        OnlineOrder resultOnlineOrder = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, onlineOrder.getIdOrder());
            pstmt.setString(2, onlineOrder.getOoDateTime());
            pstmt.setInt(3, onlineOrder.getIdCustomer());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                resultOnlineOrder = new OnlineOrder(
                        rs.getInt("id"),
                        rs.getInt("id_customer"),
                        rs.getString("oo_datetime")
                );
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }

        return resultOnlineOrder;
    }
}
