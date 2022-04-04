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
import java.util.Base64;

public class UserFilter extends AbstractFilter {

    private static final Base64.Decoder DECODER = Base64.getDecoder();
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
        //String loginURI = req.getContextPath() + "/jsp/login.jsp";
        String loginURI = req.getContextPath() + "/login";

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
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                filterChain.doFilter(servletRequest,servletResponse); //lancia errori?
            }
        }
    }

    /*private boolean authenticateUser(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, SQLException {

        final String auth = req.getHeader("Authorization");

        if(auth == null || auth.isBlank()){
            sendAuthenticationChallenge(res);
            return false;
        }

        if(!auth.toUpperCase().startsWith("BASIC ")){
            sendAuthenticationChallenge(res);
            return false;
        }

        final String pair = new String(DECODER.decode(auth.substring(6)));
        final String[] userDetails = pair.split(":",2);

        if(checkUserCredentials(userDetails[0], userDetails[1])){
            HttpSession session = req.getSession(true);
            session.setAttribute(USER_ATTRIBUTE, userDetails[0]);
            return true;
        }

        sendAuthenticationChallenge(res);
        return false;
    }

     */

    /*private boolean checkUserCredentials(String username, String password) throws SQLException, ServletException {

        //list = new GetOnlineOrderByCustomerDatabase(getDataSource().getConnection(), id).getOnlineOrderByCustomer();

        UserCredential credential = new UserCredential(
                username,
                password,
                UserCredential.TypeUser.valueOf("CUSTOMER"),
                null
        );
        try{

            credential = new CheckUserCredential(ds.getConnection(),credential).getUserCredentials();

            if (credential.getIdentification() != null) return true;

        } catch (SQLException e) {
            throw new ServletException(String.format("SQL exception while checking user credentials: %s", e.getMessage()));
        }

        return false;
    }

     */

    /*private void sendAuthenticationChallenge(HttpServletResponse res) throws IOException {

        res.setHeader("WWW-Authenticate", "Basic realm=Customer");
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

     */

    @Override
    public void destroy() {
        config = null;
        ds = null;
    }
}
