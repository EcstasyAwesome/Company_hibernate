package com.github.company.listener;

import com.github.company.database.HibernateUtil;
import com.github.company.security.SecurityImpl;
import com.github.company.servlet.positions.PositionCreate;
import com.github.company.servlet.positions.PositionDelete;
import com.github.company.servlet.positions.PositionSearch;
import com.github.company.servlet.positions.PositionUpdate;
import com.github.company.servlet.root.About;
import com.github.company.servlet.root.Authorization;
import com.github.company.servlet.root.EditProfile;
import com.github.company.servlet.root.Profile;
import com.github.company.servlet.users.UserCreate;
import com.github.company.servlet.users.UserDelete;
import com.github.company.servlet.users.UserSearch;
import com.github.company.servlet.users.UserUpdate;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static com.github.company.security.Security.*;

@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(Initializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            new SecurityImpl.Configurator(GUEST)
                    .configureServlet(PositionCreate.class, ADMIN)
                    .configureServlet(PositionSearch.class, USER)
                    .configureServlet(PositionUpdate.class, ADMIN)
                    .configureServlet(PositionDelete.class, ADMIN)
                    .configureServlet(UserCreate.class, ADMIN)
                    .configureServlet(UserSearch.class, USER)
                    .configureServlet(UserUpdate.class, ADMIN)
                    .configureServlet(UserDelete.class, ADMIN)
                    .configureServlet(Profile.class, USER)
                    .configureServlet(EditProfile.class, USER)
                    .configureServlet(Authorization.class, GUEST)
                    .configureServlet(About.class, GUEST)
                    .configureResource("/resources/")
                    .configureResource("/storage/");
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
        }
        LOGGER.info("Start server");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.close();
        LOGGER.info("Stop server");
    }
}