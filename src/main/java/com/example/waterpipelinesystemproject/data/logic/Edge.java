package com.example.waterpipelinesystemproject.data.logic;

import java.util.Objects;

public class Edge {

    private final Vertex to;

    private final int weight;

    public Edge(Vertex to, int weight) {
        this.to = Objects.requireNonNull(to);
        if (weight <= 0) throw new IllegalArgumentException("Weight expected be positive, got " + weight);
        this.weight = weight;
    }

    public Vertex getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "to=" + to +
                ", weight=" + weight +
                '}';
    }
}
