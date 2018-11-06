package com.github.company.util;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class PropUtil {

    private static final Logger LOGGER = Logger.getLogger(PropUtil.class);
    private static Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(PropUtil.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            LOGGER.fatal(e.toString());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getProperty(@NotNull String property) {
        String result;
        if ((result = PROPERTIES.getProperty(property)) == null) {
            String error = String.format("not found property '%s'", property);
            LOGGER.fatal(error);
            throw new RuntimeException(error);
        }
        return result;
    }
}