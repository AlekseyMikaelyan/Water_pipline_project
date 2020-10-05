package com.example.waterpipelinesystemproject.repository.impl;

import com.example.waterpipelinesystemproject.entity.SubProblem;
import com.example.waterpipelinesystemproject.repository.SubProblemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SubProblemRepositoryImpl implements SubProblemRepository {

    private final SessionFactory sessionFactory;

    public SubProblemRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(SubProblem subProblem) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(subProblem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubProblem> findAll() {
            Session session = this.sessionFactory.getCurrentSession();
            return session.createQuery("from SubProblem").list();
    }

    @Override
    @Transactional(readOnly = true)
    public SubProblem findById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(SubProblem.class, id);
    }
}
