package com.example.waterpipelinesystemproject.repository;

import com.example.waterpipelinesystemproject.entity.Solution;

import java.util.List;

public interface SolutionRepository {

    void create(Solution solution);
    void deleteAll();
    List<Solution> findAll();
}
