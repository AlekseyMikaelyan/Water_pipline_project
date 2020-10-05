package com.example.waterpipelinesystemproject.service;

import com.example.waterpipelinesystemproject.data.logic.Problem;
import com.example.waterpipelinesystemproject.data.logic.ProblemBuilder;
import com.example.waterpipelinesystemproject.entity.Connection;
import com.example.waterpipelinesystemproject.entity.Location;
import com.example.waterpipelinesystemproject.entity.Solution;
import com.example.waterpipelinesystemproject.entity.SubProblem;
import com.example.waterpipelinesystemproject.repository.ConnectionRepository;
import com.example.waterpipelinesystemproject.repository.LocationRepository;
import com.example.waterpipelinesystemproject.repository.SolutionRepository;
import com.example.waterpipelinesystemproject.repository.SubProblemRepository;
import com.example.waterpipelinesystemproject.service.interfaces.PathfinderService;
import com.example.waterpipelinesystemproject.util.LoadUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class InitDBService {

    private static final Logger logger = Logger.getLogger(InitDBService.class);

    private final LocationRepository locationRepository;
    private final ConnectionRepository connectionRepository;
    private final SubProblemRepository subProblemRepository;
    private final PathfinderService pathfinderService;

    public InitDBService(LocationRepository locationRepository, ConnectionRepository connectionRepository,  SubProblemRepository subProblemRepository, PathfinderService pathfinderService) {
        this.locationRepository = locationRepository;
        this.connectionRepository = connectionRepository;
        this.subProblemRepository = subProblemRepository;
        this.pathfinderService = pathfinderService;
    }

    public void init() {
        Collection<File> files = FileUtils.listFiles(
                new File(LoadUtil.getPath(LoadUtil.Folder.CSV)),
                new String[] { "csv" }, true
        );

        List<List<String>> locations = new ArrayList<>();
        List<List<String>> connections = new ArrayList<>();
        List<List<String>> problems = new ArrayList<>();

        for (File file : files) {
            try(CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()))) {
                String[] values;
                int lineNumber = 0;
                while ((values = reader.readNext()) != null) {
                    if (file.getName().endsWith("locations.csv")) {
                        locations.add(Arrays.asList(values));
                    }
                    if(lineNumber == 0) {
                        lineNumber++;
                        continue;
                    }
                    if (file.getName().endsWith("connections.csv")) {
                            connections.add(Arrays.asList(values));
                    }
                    if (file.getName().endsWith("problems.csv")) {
                            problems.add(Arrays.asList(values));
                    }
                }
            } catch (CsvValidationException | IOException e) {
                e.printStackTrace();
            }
            logger.info("Parsing data from csv files");
        }

        createLocation(locations);
        createConnection(connections);
        createProblem(problems);
        logger.info("Insert data from csv files to dataBase");

        createSolution();
        logger.info("Create solution in Solution table");

    }

    private void createLocation(List<List<String>> list) {
        List<Location> locations = locationRepository.findAll();
        if (locations.size() != list.size()) {
            for (List<String> strings : list) {
                Location location = new Location();
                location.setNumberOfLocation(strings.get(0));
                locationRepository.create(location);
                logger.info("Create locations in location table");
            }
        }
    }

    private void createConnection(List<List<String>> list) {
        List<Connection> connections = connectionRepository.findAll();
        if (connections.size() != list.size()) {
            Connection connection = new Connection();
            Location locationFrom;
            Location locationTo;
            for (List<String> strings : list) {
                locationFrom = locationRepository.findById(Integer.parseInt(strings.get(0)));
                connection.setFrom(locationFrom);
                locationTo = locationRepository.findById(Integer.parseInt(strings.get(1)));
                connection.setTo(locationTo);
                Integer cost = Integer.parseInt(strings.get(2));
                connection.setCost(cost);
                connectionRepository.create(connection);
                logger.info("Create connections in connection table");
            }
        }
    }

    private void createProblem(List<List<String>> list) {
        List<SubProblem> subProblems = subProblemRepository.findAll();
        if (subProblems.size() != list.size()) {
            SubProblem subProblem = new SubProblem();
            Location locationFrom;
            Location locationTo;
            for (List<String> strings : list) {
                locationFrom = locationRepository.findById(Integer.parseInt(strings.get(0)));
                subProblem.setFrom(locationFrom);
                locationTo = locationRepository.findById(Integer.parseInt(strings.get(1)));
                subProblem.setTo(locationTo);
                subProblemRepository.create(subProblem);
                logger.info("Create problems in problem table");
            }
        }
    }

    private void createSolution() {
        Problem problem = pathfinderService.load();
        List<Problem.Solution> solutions = problem.solve();
        pathfinderService.consume(solutions);
    }
}
