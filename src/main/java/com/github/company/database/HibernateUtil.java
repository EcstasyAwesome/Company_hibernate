package com.github.company.database;

import com.github.company.dao.entity.Group;
import com.github.company.dao.entity.Position;
import com.github.company.dao.entity.User;
import com.github.company.util.PropUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;
    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);

    static {
        try {
            SESSION_FACTORY = new Configuration()
                    .setProperty(Environment.DATASOURCE, PropUtil.getProperty("hibernate.datasource"))
                    .setProperty(Environment.SHOW_SQL, PropUtil.getProperty("hibernate.show_sql"))
                    .setProperty(Environment.DIALECT, PropUtil.getProperty("hibernate.dialect"))
                    .setProperty(Environment.HBM2DDL_AUTO, PropUtil.getProperty("hibernate.hbm2ddl.auto"))
                    .addAnnotatedClass(Group.class)
                    .addAnnotatedClass(Position.class)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            LOGGER.fatal(ex.toString());
            throw new ExceptionInInitializerError(ex);
        }
    }

    private HibernateUtil() {
    }

    public static Session getSession() throws HibernateException {
        return SESSION_FACTORY.openSession();
    }

    public static void close() throws HibernateException {
        SESSION_FACTORY.close();
    }
}