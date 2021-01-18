package com.md.pandemic_simulation.data.repository;

import com.md.pandemic_simulation.data.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SimulationRepository extends JpaRepository<Simulation, UUID> {
}
