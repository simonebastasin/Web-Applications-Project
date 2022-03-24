package it.unipd.dei.wa2122.wadteam.servlets;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LoggerServlet extends HttpServlet {

    Logger logger;

    //override the init method: here you should put the initialization of your servlet
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        logger = LogManager.getLogger(this.getClass());

        System.out.println("MESSAGE WRITTEN ON STANDARD OUT DURING INIT");
        logger.info("MESSAGE WRITTEN ON LOGGER DURING INIT");
    }


    //override destroy method: here you should put the behaviour of your servlet when destroyed
    @Override
    public void destroy() {
        super.destroy();
    }


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String str = req.getParameter("logtext");

        System.out.println("stdout: " + str);
        logger.info("log info: " + str);
        logger.debug("log debug: " + str);
        logger.error("log error: " + str);
        logger.warn("log warn: " + str);

    }


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        Logger al = LogManager.getLogger("AliceLogger");

        al.info("THIS IS A MESSAGE FROM ALICE ");

        Logger bl = LogManager.getLogger("BobLogger");

        bl.info("THIS IS A MESSAGE FROM BOB");

        Logger cl = LogManager.getLogger("AliceLogger.ClaireLogger");

        cl.info("THIS IS A MESSAGE FROM CLAIRE, ALICE'S DAUGHTER ");
    }

}