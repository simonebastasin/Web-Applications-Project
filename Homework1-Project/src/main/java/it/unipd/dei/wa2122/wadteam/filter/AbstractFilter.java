package it.unipd.dei.wa2122.wadteam.filter;

import jakarta.servlet.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;

public class AbstractFilter implements Filter {

    private static DataSource ds = null;
    private FilterConfig config = null;

    @Override
    public void init(FilterConfig config) throws ServletException {

        if (config == null){
            throw new ServletException("Filter configuration cannot be null");
        }
        this.config = config;


        try{
            ds = getDataSource();
        } catch (NamingException e) {
            ds = null;
            throw new ServletException(String.format("Impossible to access the database: %s", e.getMessage()));

        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }


    @Override
    public void destroy() {
        config = null;
        ds = null;
    }


    public DataSource getDataSource() throws NamingException {

        if (ds == null) {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/electromechanics_shop");
        }
        return ds;
    }
}
