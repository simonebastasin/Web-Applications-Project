package it.unipd.dei.wa2122.wadteam.filter;

import it.unipd.dei.wa2122.wadteam.resources.UserCredential;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;

public class EmployeeFilter extends AbstractFilter {

    private static final String USER_ATTRIBUTE = "user";
    private FilterConfig config = null;
    private DataSource ds;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig == null){
            throw new ServletException("Filter configuration cannot be null");
        }
        this.config = filterConfig;
        InitialContext ctx;
        try{
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/electromechanics_shop");

        } catch (NamingException e) {
            ds = null;
            throw new ServletException(String.format("Impossible to access the database: %s", e.getMessage()));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if(!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)){
            throw new ServletException("Only HTTP requests/responses are allowed");
        }

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/session/login";
        String unauthorizedUri = "/jsp/unauthorized.jsp";

        if(session == null){
            res.sendRedirect(loginURI);
        }
        else {
            final UserCredential user = (UserCredential) session.getAttribute(USER_ATTRIBUTE);

            if(user == null || user.getIdentification().isBlank()){
                session.invalidate();
                res.sendRedirect(loginURI);
            }
            else{
                if(!"EMPLOYEE".equalsIgnoreCase(user.getType().toString())){
                    req.getRequestDispatcher(unauthorizedUri).forward(req, res);
                }
                else{
                    res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                    res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                    filterChain.doFilter(servletRequest,servletResponse);
                }
            }
        }
    }


    @Override
    public void destroy() {
        config = null;
        ds = null;
    }
}
