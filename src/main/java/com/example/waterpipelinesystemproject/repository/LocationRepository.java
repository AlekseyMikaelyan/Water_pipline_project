package com.example.waterpipelinesystemproject.repository;

import com.example.waterpipelinesystemproject.entity.Location;

import java.util.List;

public interface LocationRepository {

    void create(Location location);
    List<Location> findAll();
    Location findById(Integer id);
}
