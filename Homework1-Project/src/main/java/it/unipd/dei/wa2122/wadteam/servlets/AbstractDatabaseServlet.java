package it.unipd.dei.wa2122.wadteam.servlets;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import javax.sql.DataSource;

/**
 * Gets the {@code DataSource} for managing the connection pool to the database.
 *
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version HW1.00
 * @since 1.00
 */
public abstract class AbstractDatabaseServlet extends AbstractLoggerServlet {

    /**
     * The connection pool to the database.
     */
    private DataSource ds;

    /**
     * Gets the {@code DataSource} for managing the connection pool to the database.
     *
     * @param config
     *          a {@code ServletConfig} object containing the servlet's
     *          configuration and initialization parameters.
     *
     * @throws ServletException
     *          if an exception has occurred that interferes with the servlet's normal operation
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // the JNDI lookup context
        InitialContext cxt;

        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/electromechanics_shop");
        } catch (NamingException e) {
            ds = null;

            throw new ServletException(
                    String.format("Impossible to access the connection pool to the database: %s",
                            e.getMessage()));
        }
    }

    /**
     * Releases the {@code DataSource} for managing the connection pool to the database.
     */
    public void destroy() {
        ds = null;
    }

    /**
     * Returns the {@code DataSource} for managing the connection pool to the database.
     *
     * @return the {@code DataSource} for managing the connection pool to the database
     */
    protected final DataSource getDataSource() {
        return ds;
    }

}


