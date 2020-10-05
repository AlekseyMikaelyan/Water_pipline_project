package com.example.waterpipelinesystemproject.service;

import com.example.waterpipelinesystemproject.data.logic.Problem;
import com.example.waterpipelinesystemproject.data.logic.ProblemBuilder;
import com.example.waterpipelinesystemproject.entity.Connection;
import com.example.waterpipelinesystemproject.entity.Location;
import com.example.waterpipelinesystemproject.entity.Solution;
import com.example.waterpipelinesystemproject.entity.SubProblem;
import com.example.waterpipelinesystemproject.repository.ConnectionRepository;
import com.example.waterpipelinesystemproject.repository.LocationRepository;
import com.example.waterpipelinesystemproject.repository.SubProblemRepository;
import com.example.waterpipelinesystemproject.repository.SolutionRepository;
import com.example.waterpipelinesystemproject.service.interfaces.PathfinderService;
import com.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Types;
import java.util.*;

@Service
@Transactional
public class PathfinderServiceImpl implements PathfinderService {

    private static final Logger logger = Logger.getLogger(PathfinderServiceImpl.class);

    private final LocationRepository locationRepository;
    private final ConnectionRepository connectionRepository;
    private final SubProblemRepository subProblemRepository;
    private final SolutionRepository solutionRepository;

    public PathfinderServiceImpl(LocationRepository locationRepository, ConnectionRepository connectionRepository, SubProblemRepository subProblemRepository, SolutionRepository solutionRepository) {
        this.locationRepository = locationRepository;
        this.connectionRepository = connectionRepository;
        this.subProblemRepository = subProblemRepository;
        this.solutionRepository = solutionRepository;
    }

    @Override
    public Problem load() {
        List<Location> locations = locationRepository.findAll();
        List<SubProblem> subProblems = subProblemRepository.findAll();
        List<Connection> connections = connectionRepository.findAll();
        int size = locations.size();
        ProblemBuilder problemBuilder = new ProblemBuilder(size);

        int index = 0;

        Map<Integer, Integer> indexById = new HashMap<>();
        for (Location location : locations) {
            problemBuilder.name(index, location.getNumberOfLocation());
            indexById.put(location.getId(), index);
            index++;
        }

        for (Connection connection : connections) {
            int from = indexById.get(connection.getFrom().getId());
            int to = indexById.get(connection.getTo().getId());
            problemBuilder.connect(from, to, connection.getCost());
        }

        for (SubProblem subProblem : subProblems) {
            problemBuilder.solve(
                    subProblem.getId(),
                    indexById.get(subProblem.getFrom().getId()),
                    indexById.get(subProblem.getTo().getId())
            );
        }

        return problemBuilder.build();
    }

    @Override
    public void consume(Collection<Problem.Solution> solutions) {
        solutionRepository.deleteAll();
        for (Problem.Solution problemSolution : solutions) {
            int problemId = problemSolution.getProblemId();
            Solution solution = new Solution();
            solution.setProblemId(problemId);
            solution.setSubProblem(subProblemRepository.findById(problemId));
            if (problemSolution instanceof Problem.RouteFound) {
                solution.setCost(((Problem.RouteFound)problemSolution).getDistance());
            } else {
                solution.setCost(Types.INTEGER);
            }
            solutionRepository.create(solution);
            logger.info("Create solutions of problems!");

            createCSVFile();
        }
    }

    public void createCSVFile() {
        List<Solution> solutionList = solutionRepository.findAll();
        String csv = "solutions.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csv))) {

            String[] header = "ROUTE EXISTS,MIN LENGTH".split(",");

            writer.writeNext(header);

            for (int i = 0; i < solutionList.size(); i++) {
                String[] data  = ("True" + "," + solutionList.get(i).getCost()).split(",");
                writer.writeNext(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
