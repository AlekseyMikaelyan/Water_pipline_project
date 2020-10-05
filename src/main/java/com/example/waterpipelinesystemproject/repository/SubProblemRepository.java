package com.example.waterpipelinesystemproject.repository;

import com.example.waterpipelinesystemproject.entity.SubProblem;

import java.util.List;

public interface SubProblemRepository {

    void create(SubProblem subProblem);
    List<SubProblem> findAll();
    SubProblem findById(Integer id);
}
