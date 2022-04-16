package it.unipd.dei.wa2122.wadteam.filter;

import jakarta.servlet.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;

abstract class AbstractFilter implements Filter {

    private FilterConfig config = null;

    @Override
    public void init(FilterConfig config) throws ServletException {

        if (config == null){
            throw new ServletException("Filter configuration cannot be null");
        }
        this.config = config;

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }


    @Override
    public void destroy() {
        config = null;
    }

}
