package it.unipd.dei.wa2122.wadteam.filter;

import it.unipd.dei.wa2122.wadteam.resources.TypeUserEnum;
import it.unipd.dei.wa2122.wadteam.resources.UserCredential;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginFilter extends AbstractFilter{

    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if(!(servletRequest instanceof final HttpServletRequest req) || !(servletResponse instanceof final HttpServletResponse res)){
            throw new ServletException("Only HTTP requests/responses are allowed");
        }

        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/session/login";

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
                if (user.getType() == TypeUserEnum.EMPLOYEE || user.getType() == TypeUserEnum.CUSTOMER) {
                    res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                    res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    unauthorized(req,res);
                }
            }
        }
    }

}
