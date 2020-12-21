package com.md.pandemic_simulation.web.service;

import com.md.pandemic_simulation.data.model.DaySummary;
import com.md.pandemic_simulation.data.model.Simulation;
import com.md.pandemic_simulation.data.model.SimulationView;
import com.md.pandemic_simulation.data.repository.DaySummaryRepository;
import com.md.pandemic_simulation.data.repository.SimulationRepository;
import com.md.pandemic_simulation.web.dto.CreateSimulationDto;
import com.md.pandemic_simulation.web.dto.SimulationDetailsDto;
import com.md.pandemic_simulation.web.generator.PopulationGenerator;
import com.md.pandemic_simulation.web.mapper.SimulationMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SimulationServiceImpl implements SimulationService {

    private final SimulationRepository simulationRepository;
    private final DaySummaryRepository daySummaryRepository;
    private final PopulationGenerator populationGenerator;
    private SimulationMapper simulationMapper = Mappers.getMapper(SimulationMapper.class);
    private static final String SIMULATION_NOT_FOUND = "SIMULATION WITH GIVEN ID NOT FOUND. ID: ";

    public SimulationServiceImpl(SimulationRepository simulationRepository, DaySummaryRepository daySummaryRepository, PopulationGenerator populationGenerator) {
        this.simulationRepository = simulationRepository;
        this.daySummaryRepository = daySummaryRepository;
        this.populationGenerator = populationGenerator;
    }

    @Transactional
    @Override
    public void createSimulation(CreateSimulationDto simulationDto) {
        // Simulation createdSimulation = simulation(simulationDto);
        Simulation simulation = Simulation.builder()
                .P(100)
                .I(20)
                .M(0.5)
                .R(1)
                .N("moja")
                .Ts(10)
                .Ti(2)
                .Tm(1)
                .build();
        List<DaySummary> daysOfEpidemic = populationGenerator.generate(simulation);
        simulation.setEpidemicDays(daysOfEpidemic);
        Simulation saved = simulationRepository.save(simulation);
        log.info("Saved simulation with id: {}", saved.getId());
    }



/*    public static void main(String[] args) {
        PopulationGenerator populationGenerator = new BasicPopulationGenerator();
        Simulation simulation = Simulation.builder()
                .P(100)
                .I(20)
                .M(0.5)
                .R(1)
                .N("moja")
                .Ts(10)
                .Ti(2)
                .Tm(1)
                .build();
        List<DaySummary> days = populationGenerator.generate(simulation);
        days.forEach(System.out::println);
    }*/


    @Override
    public List<SimulationView> getSimulations() {
        List<SimulationView> simulations = simulationRepository.findAllBy();
        log.info("Returned {} simulations", simulations.size());
        return simulations;
    }

    @Transactional
    @Override
    public void updateSimulation(UUID id, CreateSimulationDto simulationDto) {
        Simulation fromDb = simulationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SIMULATION_NOT_FOUND + id));
        log.info("Found simulation with id: {}", fromDb.getId());

        deleteOldEpidemicDays(fromDb);

        BeanUtils.copyProperties(simulationDto, fromDb);
        log.info("Updated simulation with id: {}", fromDb.getId());

        var newDays = populationGenerator.generate(fromDb);
        log.info("Generate new data about epidemic days");

        fromDb.setEpidemicDays(newDays);
        log.info("Save new data about epidemic days.");
    }

    @Transactional
    void deleteOldEpidemicDays(Simulation simulation) {
        simulation.getEpidemicDays().forEach(daySummary -> daySummaryRepository.deleteById(daySummary.getId()));
        log.info("Removed all days drom simulation with id {}", simulation.getId());
    }

    @Transactional
    @Override
    public void removeSimulation(UUID id) {
        simulationRepository.deleteById(id);
        log.info("Deleted simulation with id: {}", id);
    }

    @Override
    public SimulationDetailsDto getSimulationDetails(UUID id) {
        SimulationDetailsDto simulationFromDB = simulationRepository.findById(id)
                .map(simulation -> simulationMapper.mapToSimulationDetailsDto(simulation))
                .orElseThrow(() -> new EntityNotFoundException(SIMULATION_NOT_FOUND + id));

        log.info("Found simulation with id: {}", simulationFromDB.getId());

        return simulationFromDB;
    }

}
