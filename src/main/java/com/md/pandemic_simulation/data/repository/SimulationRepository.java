package com.md.pandemic_simulation.data.repository;

import com.md.pandemic_simulation.data.model.Simulation;
import com.md.pandemic_simulation.data.model.SimulationView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SimulationRepository extends JpaRepository<Simulation, UUID> {
    List<SimulationView> findAllBy();
}
