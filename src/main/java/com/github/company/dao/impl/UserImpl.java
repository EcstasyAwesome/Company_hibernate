package com.github.company.dao.impl;

import com.github.company.dao.model.UserDao;
import com.github.company.dao.entity.User;
import com.github.company.database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserImpl implements UserDao {

    private static UserImpl instance = new UserImpl();

    private UserImpl() {
    }

    public static UserImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            query.select(query.from(User.class));
            Query<User> userQuery = session.createQuery(query);
            return userQuery.getResultList();
        }
    }

    @Override
    public User getByLogin(String login) throws PersistenceException {
        User result;
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("login"), login));
            Query<User> userQuery = session.createQuery(query);
            result = userQuery.getSingleResult();
        } catch (PersistenceException e) {
            String noResult = "Пользователь '%s' не зарегистрирован";
            throw new PersistenceException(String.format(noResult, login));
        }
        return result;
    }

    @Override
    public void create(User newInstance) throws ConstraintViolationException {
        Session session = HibernateUtil.getSession();
        String login = newInstance.getLogin();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get("login"), login));
            Query<User> userQuery = session.createQuery(query);
            if (!userQuery.list().isEmpty()) {
                String duplicate = "Логин '%s' уже существует";
                throw new ConstraintViolationException(String.format(duplicate, login), null, login);
            }
            session.beginTransaction();
            session.save(newInstance);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public void update(User instance) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            User user = session.load(User.class, instance.getId());
            if (instance.getSurname() != null) user.setSurname(instance.getSurname());
            if (instance.getFirstName() != null) user.setFirstName(instance.getFirstName());
            if (instance.getMiddleName() != null) user.setMiddleName(instance.getMiddleName());
            if (instance.getAvatar() != null) user.setAvatar(instance.getAvatar());
            if (instance.getPhone() != 0) user.setPhone(instance.getPhone());
            if (instance.getLogin() != null) user.setLogin(instance.getLogin());
            if (instance.getPassword() != null) user.setPassword(instance.getPassword());
            if (instance.getRegisterDate() != null) user.setRegisterDate(instance.getRegisterDate());
            if (instance.getGroup() != null) user.setGroup(instance.getGroup());
            if (instance.getPosition() != null) user.setPosition(instance.getPosition());
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public int countPages(int recordsOnPage) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            query.select(builder.count(query.from(User.class)));
            Query<Long> longQuery = session.createQuery(query);
            long records = longQuery.getSingleResult();
            return (int) Math.ceil(records * 1.0 / recordsOnPage);
        }
    }

    @Override
    public List<User> getPage(int page, int recordsOnPage) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            query.select(query.from(User.class));
            Query<User> userQuery = session.createQuery(query);
            userQuery.setFirstResult(page * recordsOnPage - recordsOnPage);
            userQuery.setMaxResults(recordsOnPage);
            return userQuery.getResultList();
        }
    }
}