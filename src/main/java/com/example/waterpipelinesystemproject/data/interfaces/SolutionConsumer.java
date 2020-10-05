package com.example.waterpipelinesystemproject.data.interfaces;

import com.example.waterpipelinesystemproject.data.logic.Problem;

import java.util.Collection;

public interface SolutionConsumer {
    void consume(Collection<Problem.Solution> solutions);
}
