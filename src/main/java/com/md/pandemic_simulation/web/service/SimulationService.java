package com.md.pandemic_simulation.web.service;

import com.md.pandemic_simulation.data.model.SimulationView;
import com.md.pandemic_simulation.web.dto.CreateSimulationDto;
import com.md.pandemic_simulation.web.dto.SimulationDetailsDto;

import java.util.List;
import java.util.UUID;

public interface SimulationService {
    void createSimulation(CreateSimulationDto simulationDto);

    List<SimulationView> getSimulations();

    void updateSimulation(UUID id, CreateSimulationDto simulationDto);

    void removeSimulation(UUID id);

    SimulationDetailsDto getSimulationDetails(UUID id);


}
