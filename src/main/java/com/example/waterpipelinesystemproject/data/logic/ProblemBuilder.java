package com.example.waterpipelinesystemproject.data.logic;

import java.util.*;

public class ProblemBuilder {
    private final String[] names;
    private final Map<String, Integer> indices;
    private final int size;
    private final int[][] adjacency;
    private final Map<Integer, Problem.Route> problems;

    public ProblemBuilder(int size) {
        this.names = new String[size];
        this.size = size;
        this.adjacency = new int[size][size];
        this.indices = new HashMap<>(size);
        this.problems = new LinkedHashMap<>();
    }

    public ProblemBuilder name(int index, String value) {
        names[index] = value;

        if (indices.put(value, index) != null)
            throw new IllegalArgumentException("Duplicate vertex names are not allowed! Duplicate: " + value);

        return this;
    }

    public ProblemBuilder connect(int from, int to, int weight) {
        if (weight <= 0) throw new IllegalArgumentException("Weight expected to be positive, got " + weight);
        adjacency[from][to] = weight;
        return this;
    }

    public ProblemBuilder connect(String from, String to, int weight) {
        return connect(indices.get(from), indices.get(to), weight);
    }

    public ProblemBuilder connectBiDirectional(int from, int to, int weight) {
        if (weight <= 0) throw new IllegalArgumentException("Weight expected to be positive, got " + weight);
        adjacency[from][to] = weight;
        adjacency[to][from] = weight;
        return this;
    }

    public ProblemBuilder connectBiDirectional(String from, String to, int weight) {
        return connectBiDirectional(indices.get(from), indices.get(to), weight);
    }

    public ProblemBuilder solve(int problemId, int from, int to) {
        Objects.checkIndex(from, size);
        Objects.checkIndex(to, size);
        problems.put(problemId, new Problem.Route(problemId, from, to));
        return this;
    }

    public ProblemBuilder solve(int problemId, String from, String to) {
        problems.put(problemId, new Problem.Route(problemId, indices.get(from), indices.get(to)));
        return this;
    }

    public Problem build() {
        var vertices = new ArrayList<Vertex>(size);
        var connections = new ArrayList<List<Edge>>(size);
        for (int i = 0; i < size; i++) {
            var edges = new ArrayList<Edge>();
            var vertex = new Vertex(i, names[i], edges);
            vertices.add(vertex);
            connections.add(edges);
        }

        for (var vertex : vertices) {
            int index = vertex.getIndex();
            var edges = connections.get(index);
            var adjacent = adjacency[index];
            for (int i = 0; i < size; i++) {
                int neighbor = adjacent[i];
                if (neighbor == 0) continue;
                edges.add(new Edge(vertices.get(i), neighbor));
            }
        }

        return new Problem(vertices, problems.values());
    }
}
