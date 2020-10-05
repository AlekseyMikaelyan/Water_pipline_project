package com.example.waterpipelinesystemproject;

import com.example.waterpipelinesystemproject.data.logic.Problem;
import com.example.waterpipelinesystemproject.service.interfaces.PathfinderService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WaterpipelinesystemprojectApplicationTests {

    @Autowired
    private PathfinderService pathfinderService;

    @Test
    void contextLoads() {
        Problem problem = pathfinderService.load();
        List<Problem.Solution> solutions = problem.solve();
        pathfinderService.consume(solutions);
    }

}
