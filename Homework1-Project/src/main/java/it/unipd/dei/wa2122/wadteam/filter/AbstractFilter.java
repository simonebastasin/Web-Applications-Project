package it.unipd.dei.wa2122.wadteam.filter;

import it.unipd.dei.wa2122.wadteam.resources.GenericError;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

abstract class AbstractFilter implements Filter {

    private FilterConfig config = null;
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    @Override
    public void init(FilterConfig config) throws ServletException {

        if (config == null){
            throw new ServletException("Filter configuration cannot be null");
        }
        this.config = config;

    }


    @Override
    public void destroy() {
        config = null;
    }

    public void unauthorized(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String unauthorizedUri = "/jsp/unauthorized.jsp";
        if (req.getServletPath().startsWith("/rest/") || req.getHeader("Accept").contains("application/json")) {
            res.setContentType(JSON_UTF_8_MEDIA_TYPE);
            res.getWriter().write(GenericError.UNAUTHORIZED.toJSON().toString(2));
        } else {
            res.setStatus(401);
            req.getRequestDispatcher(unauthorizedUri).forward(req, res);
        }
    }

}
