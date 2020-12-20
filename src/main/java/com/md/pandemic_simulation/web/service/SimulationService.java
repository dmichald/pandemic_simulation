package com.md.pandemic_simulation.web.service;

import com.md.pandemic_simulation.data.model.SimulationName;
import com.md.pandemic_simulation.web.dto.CreateSimulationDto;

public interface SimulationService {
    void createSimulation(CreateSimulationDto simulationDto);

    SimulationName getSimulations();

}
