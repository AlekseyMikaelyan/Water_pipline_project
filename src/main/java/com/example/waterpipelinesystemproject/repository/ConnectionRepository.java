package com.example.waterpipelinesystemproject.repository;

import com.example.waterpipelinesystemproject.entity.Connection;

import java.util.List;

public interface ConnectionRepository {

    void create(Connection connection);
    List<Connection> findAll();
}
