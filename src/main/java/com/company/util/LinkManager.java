package com.company.util;

import com.company.servlet.Authorization;
import com.company.servlet.Main;
import com.company.servlet.positions.PositionCreate;
import com.company.servlet.positions.PositionDelete;
import com.company.servlet.positions.PositionSearch;
import com.company.servlet.positions.PositionUpdate;
import com.company.servlet.users.UserCreate;
import com.company.servlet.users.UserDelete;
import com.company.servlet.users.UserSearch;
import com.company.servlet.users.UserUpdate;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class LinkManager {

    private static LinkManager instance;
    private final Map<String, Page> list = new HashMap<>();

    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE_REGISTER = "/WEB-INF/jsp/registration.jsp";

    private LinkManager() {
        list.put(Authorization.AUTHORIZATION, new Page(null, Page.GUEST));
        list.put(Main.MAIN, new Page("/index.jsp", Page.GUEST));
        list.put(Main.PROFILE, new Page("/WEB-INF/jsp/profile.jsp", Page.USER));
        list.put(Main.EDIT, new Page("/WEB-INF/jsp/edit.jsp", Page.USER));
        list.put(Main.ABOUT, new Page("/WEB-INF/jsp/about.jsp", Page.GUEST));
        list.put(UserSearch.MAIN, new Page("/WEB-INF/jsp/users_search.jsp", Page.USER));
        list.put(UserCreate.ADD, new Page("/WEB-INF/jsp/users_add.jsp", Page.ADMIN));
        list.put(UserUpdate.UPDATE, new Page("/WEB-INF/jsp/users_update.jsp", Page.ADMIN));
        list.put(UserDelete.DELETE, new Page("/WEB-INF/jsp/users_delete.jsp", Page.ADMIN));
        list.put(PositionSearch.MAIN, new Page("/WEB-INF/jsp/positions_search.jsp", Page.USER));
        list.put(PositionCreate.ADD, new Page("/WEB-INF/jsp/positions_add.jsp", Page.ADMIN));
        list.put(PositionUpdate.UPDATE, new Page("/WEB-INF/jsp/positions_update.jsp", Page.ADMIN));
        list.put(PositionDelete.DELETE, new Page("/WEB-INF/jsp/positions_delete.jsp", Page.ADMIN));
    }

    public static LinkManager getInstance() {
        if (instance == null) return instance = new LinkManager();
        return instance;
    }

    public Map<String, Page> getList() {
        return list;
    }

    /**
     * @param link RequestURI
     * @return returns true if requestURI is a resource link
     * @see #isResources(String)
     */

    public boolean isResources(@NotNull String link) {
        String path1 = "/resources/";
        String path2 = "/storage/";
        return link.startsWith(path1) | link.startsWith(path2);
    }

    public class Page {

        public static final int GUEST = 1;
        public static final int USER = 2;
        public static final int ADMIN = 3;

        private String path;
        private int access;

        private Page(String path, int access) {
            this.path = path;
            this.access = access;
        }

        public String getPath() {
            return path;
        }

        public int getAccess() {
            return access;
        }
    }
}