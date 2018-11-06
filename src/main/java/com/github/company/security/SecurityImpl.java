package com.github.company.security;

import com.github.company.dao.entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.annotation.WebServlet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SecurityImpl implements Security {

    private static final Map<String, Integer> links = new HashMap<>();
    private static final Set<String> resources = new HashSet<>();
    private static SecurityImpl instance = new SecurityImpl();

    public static SecurityImpl getInstance() {
        return instance;
    }

    private SecurityImpl() {
    }

    public static class Configurator {

        public Configurator(int indexPageAccess) {
            links.put("/", indexPageAccess);
            System.out.println("def configurator");
        }

        public Configurator configureServlet(@NotNull Class<?> annotatedClass, int access) throws IllegalArgumentException {
            if (annotatedClass.isAnnotationPresent(WebServlet.class)) {
                for (String url : annotatedClass.getAnnotation(WebServlet.class).urlPatterns()) {
                    links.put(url, access);
                }
            } else throw new IllegalArgumentException("Only annotated classes are supported");
            return this;
        }

        public Configurator configureResource(@NotNull String resourceRootFolder) throws IllegalArgumentException {
            if (resourceRootFolder.matches("^/\\w+/$")) resources.add(resourceRootFolder);
            else throw new IllegalArgumentException("Incorrect path");
            return this;
        }

        public Security configure() {
            return getInstance();
        }
    }

    @Override
    public int verify(@Nullable User user, @NotNull String link) {
        if (links.containsKey(link)) {
            if (links.get(link) == GUEST) return SUCCESS;
            else if (user == null) return UNAUTHORIZED;
            else if (user.getGroup().getId() >= links.get(link)) return SUCCESS;
            else return FORBIDDEN;
        } else if (resources.contains(link.substring(0, link.substring(1).indexOf("/") + 2))) return SUCCESS;
        return NOT_FOUND;
    }
}