package com.md.pandemic_simulation.data.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface SimulationView {

    UUID getId();

    @Value("#{target.N}")
    String getN();
}
