package com.example.waterpipelinesystemproject.data.logic;

import com.example.waterpipelinesystemproject.util.FibonacciHeap;

import java.util.*;
import java.util.stream.Collectors;

public class Problem {
    private final List<Route> routesToCalculate;

    private final List<Vertex> vertices;

    private final int[][] distanceMatrix;

    private final BitSet[] settled;

    private final int size;

    public Problem(Collection<Vertex> vertices, Collection<Route> routesToCalculate) {
        this.vertices = List.copyOf(vertices);
        this.size = vertices.size();
        distanceMatrix = new int[size][size];
        for (int[] row : distanceMatrix) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        settled = new BitSet[size];
        for (int i = 0; i < size; i++) {
            distanceMatrix[i][i] = 0;
            settled[i] = new BitSet(size);
        }
        this.routesToCalculate = List.copyOf(routesToCalculate);
    }

    public int size() {
        return size;
    }

    public Vertex vertex(int index) {
        return vertices.get(index);
    }

    public List<Solution> solve() {
        return routesToCalculate.stream()
                .map(this::solveRoute)
                .collect(Collectors.toList());
    }

    private Solution solveRoute(Route route) {
        var from = route.from;
        var to = route.to;

        int distance = dijkstra(from, to);

        return distance < Integer.MAX_VALUE
                ? new RouteFound(route, distance)
                : new RouteNotFound(route);
    }

    private int dijkstra(int from, int to) {
        if (from == to) return 0;

        var settled = this.settled[from];
        if (settled.get(to)) return distanceMatrix[from][to];

        var distances = distanceMatrix[from];

        var closest = new FibonacciHeap<Vertex>();
        var entries = new ArrayList<FibonacciHeap.Entry<Vertex>>(size);
        for (int i = 0; i < size; i++) {
            FibonacciHeap.Entry<Vertex> entry = settled.get(i)
                    ? null
                    : closest.enqueue(vertex(i), distances[i]);
            entries.add(entry);
        }

        while (!closest.isEmpty()) {
            var entry = closest.dequeueMin();
            var vertex = entry.getValue();

            int weight = entry.getPriority();

            for (var edge : vertex.getEdges()) {
                var neighbor = edge.getTo();
                int neighborIndex = neighbor.getIndex();
                // skip visited nodes
                if (settled.get(neighborIndex)) continue;

                int distance = weight + edge.getWeight();

                if (distance < distances[neighborIndex]) {
                    distances[neighborIndex] = distance;

                    closest.decreaseKey(entries.get(neighborIndex), distance);
                }
            }
            settled.set(vertex.getIndex());

            if (vertex.getIndex() == to) return weight;
        }

        return Integer.MAX_VALUE;
    }

    public static final class Route {

        private final int problemId;

        private final int from;

        private final int to;

        public Route(int problemId, int from, int to) {
            this.problemId = problemId;
            this.from = from;
            this.to = to;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }

    public abstract class Solution {

        private final int problemId;

        private final Vertex from;

        private final Vertex to;

        private Solution(Route route) {
            this.problemId = route.problemId;
            this.from = vertex(route.getFrom());
            this.to = vertex(route.getTo());
        }

        public Vertex getFrom() {
            return from;
        }

        public Vertex getTo() {
            return to;
        }

        public int getProblemId() {
            return problemId;
        }

        public abstract String asText();

    }

    public final class RouteNotFound extends Solution {

        private RouteNotFound(Route route) {
            super(route);
        }

        @Override
        public String asText() {
            return "route not found";
        }
    }

    public final class RouteFound extends Solution {

        private final int distance;

        private RouteFound(Route route, int distance) {
            super(route);
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public String asText() {
            return String.valueOf(distance);
        }
    }
}
