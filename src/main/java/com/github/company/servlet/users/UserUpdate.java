package com.github.company.servlet.users;

import com.github.company.dao.DaoService;
import com.github.company.dao.entity.Group;
import com.github.company.dao.entity.Position;
import com.github.company.dao.entity.User;
import com.github.company.dao.model.GroupDao;
import com.github.company.dao.model.PositionDao;
import com.github.company.dao.model.UserDao;
import com.github.company.util.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Users update", urlPatterns = "/users/update")
public class UserUpdate extends HttpServlet {

    private UserDao userDao = DaoService.getInstance().getUserDao();
    private PositionDao positionDao = DaoService.getInstance().getPositionDao();
    private GroupDao groupDao = DaoService.getInstance().getGroupDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        if (query != null && query.matches("^id=\\d+$")) {
            long id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("positions", positionDao.getAll());
            req.setAttribute("groups", groupDao.getAll());
            req.setAttribute("user", userDao.get(id));
            Dispatcher.dispatch(req, resp, "users_update");
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = new User();
        Position position = new Position();
        Group group = new Group();
        long userId = 0;
        long groupId = 0;
        long positionId = 0;
        try {
            position.setId(positionId = Long.parseLong(req.getParameter("position")));
            group.setId(groupId = Long.parseLong(req.getParameter("group")));
            user.setId(userId = Long.parseLong(req.getParameter("id")));
            user.setSurname(req.getParameter("surname"));
            user.setFirstName(req.getParameter("firstName"));
            user.setMiddleName(req.getParameter("middleName"));
            user.setPhone(Long.parseLong(req.getParameter("phone")));
            user.setPosition(position);
            user.setGroup(group);
            userDao.update(user);
            resp.sendRedirect("/users");
        } catch (Exception e) {
            req.setAttribute("userError", e.getLocalizedMessage());
            req.setAttribute("user", userDao.get(userId));
            req.setAttribute("position", positionDao.get(positionId));
            req.setAttribute("group", groupDao.get(groupId));
            Dispatcher.dispatch(req, resp, "users_update");
        }
    }
}