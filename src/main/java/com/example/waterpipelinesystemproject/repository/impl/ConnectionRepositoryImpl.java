package com.example.waterpipelinesystemproject.repository.impl;

import com.example.waterpipelinesystemproject.entity.Connection;
import com.example.waterpipelinesystemproject.repository.ConnectionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ConnectionRepositoryImpl implements ConnectionRepository {

    private final SessionFactory sessionFactory;

    public ConnectionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Connection connection) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(connection);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Connection> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Connection").list();
    }
}
