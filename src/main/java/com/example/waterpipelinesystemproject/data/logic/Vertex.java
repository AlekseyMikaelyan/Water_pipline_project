package com.example.waterpipelinesystemproject.data.logic;

import java.util.Collections;
import java.util.List;

public class Vertex {
    private final int index;
    private final String name;
    private final List<Edge> edges;

    public Vertex(int index, String name, List<Edge> edges) {
        this.index = index;
        this.name = name;
        this.edges = Collections.unmodifiableList(edges);
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        return index == vertex.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "index=" + index +
                ", name='" + name + '\'' +
                '}';
    }
}
