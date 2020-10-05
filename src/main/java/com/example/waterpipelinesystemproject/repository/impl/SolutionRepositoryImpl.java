package com.example.waterpipelinesystemproject.repository.impl;

import com.example.waterpipelinesystemproject.entity.Solution;
import com.example.waterpipelinesystemproject.repository.SolutionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SolutionRepositoryImpl implements SolutionRepository {

    private final SessionFactory sessionFactory;

    public SolutionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Solution solution) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(solution);
    }

    @Override
    @Transactional
    public void deleteAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Solution> solutions = findAll();
        for (Solution solution : solutions) {
            session.delete(solution);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Solution> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Solution").list();
    }
}
