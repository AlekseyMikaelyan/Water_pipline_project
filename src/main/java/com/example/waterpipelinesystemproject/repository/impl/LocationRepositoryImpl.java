package com.example.waterpipelinesystemproject.repository.impl;

import com.example.waterpipelinesystemproject.entity.Location;
import com.example.waterpipelinesystemproject.repository.LocationRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LocationRepositoryImpl implements LocationRepository {

    private final SessionFactory sessionFactory;

    public LocationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Location location) {
        Session session = sessionFactory.getCurrentSession();
        session.save(location);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Location").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Location findById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Location.class, id);
    }
}
