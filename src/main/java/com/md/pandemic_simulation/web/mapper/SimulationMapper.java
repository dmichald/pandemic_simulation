package com.md.pandemic_simulation.web.mapper;

import com.md.pandemic_simulation.data.model.Simulation;
import com.md.pandemic_simulation.web.dto.CreateSimulationDto;
import com.md.pandemic_simulation.web.dto.SimulationDetailsDto;
import com.md.pandemic_simulation.web.dto.SimulationDto;
import org.mapstruct.Mapper;

@Mapper
public interface SimulationMapper {
    SimulationDetailsDto mapToSimulationDetailsDto(Simulation simulation);

    Simulation mapToSimulation(CreateSimulationDto simulationDto);

    SimulationDto mapToSimulationDto(Simulation simulation);
}
