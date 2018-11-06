package com.github.company.dao.impl;

import com.github.company.dao.entity.Group;
import com.github.company.dao.model.GroupDao;
import com.github.company.database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class GroupImpl implements GroupDao {

    private static GroupImpl instance = new GroupImpl();

    private GroupImpl() {
    }

    public static GroupImpl getInstance() {
        return instance;
    }

    private final String duplicate = "Группа '%s' уже существует";

    @Override
    public List<Group> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Group> query = builder.createQuery(Group.class);
            query.select(query.from(Group.class));
            Query<Group> groupQuery = session.createQuery(query);
            return groupQuery.getResultList();
        }
    }

    @Override
    public void create(Group newInstance) throws ConstraintViolationException {
        Session session = HibernateUtil.getSession();
        String name = newInstance.getName();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Group> query = builder.createQuery(Group.class);
            Root<Group> root = query.from(Group.class);
            query.select(root).where(builder.equal(root.get("name"), name));
            Query<Group> groupQuery = session.createQuery(query);
            if (!groupQuery.list().isEmpty())
                throw new ConstraintViolationException(String.format(duplicate, name), null, name);
            session.beginTransaction();
            session.save(newInstance);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }

    @Override
    public Group get(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(Group.class, id);
        }
    }

    @Override
    public void update(Group instance) {
        Session session = HibernateUtil.getSession();
        String name = instance.getName();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Group> query = builder.createQuery(Group.class);
            Root<Group> root = query.from(Group.class);
            query.select(root).where(builder.equal(root.get("name"), name));
            Query<Group> groupQuery = session.createQuery(query);
            if (!groupQuery.list().isEmpty()) {
                Group group = groupQuery.getSingleResult();
                if (group.getId() != instance.getId())
                    throw new ConstraintViolationException(String.format(duplicate, name), null, name);
            }
            session.clear();
            session.beginTransaction();
            session.update(instance);
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
            Group group = session.load(Group.class, id);
            session.delete(group);
            session.getTransaction().commit();
        } finally {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            session.close();
        }
    }
}