package com.md.pandemic_simulation.web.mapper;

import com.md.pandemic_simulation.data.model.Simulation;
import com.md.pandemic_simulation.web.dto.SimulationDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimulationMapper {
    SimulationMapper INSTANCE = Mappers.getMapper(SimulationMapper.class);

    SimulationDetailsDto mapToSimulationDetailsDto(Simulation simulation);
}
