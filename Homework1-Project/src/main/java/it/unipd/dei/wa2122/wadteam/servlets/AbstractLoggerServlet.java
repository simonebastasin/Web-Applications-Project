package it.unipd.dei.wa2122.wadteam.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractLoggerServlet extends AbstractServlet {
    protected Logger logger;

    //override the init method: here you should put the initialization of your servlet
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        logger = LogManager.getLogger(this.getClass());

        logger.info("INIT LOGGER "+ this.getClass().getSimpleName());
    }
}
