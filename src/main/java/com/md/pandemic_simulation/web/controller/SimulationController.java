package com.md.pandemic_simulation.web.controller;

import com.md.pandemic_simulation.data.model.SimulationView;
import com.md.pandemic_simulation.web.dto.CreateSimulationDto;
import com.md.pandemic_simulation.web.dto.SimulationDetailsDto;
import com.md.pandemic_simulation.web.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("/simulations")
@RequiredArgsConstructor
public class SimulationController {
    private final SimulationService simulationService;

    @GetMapping
    List<SimulationView> getAllSimulations() {
        return simulationService.getSimulations();
    }


    @GetMapping("/{id}")
    SimulationDetailsDto getSimulationDetails(@PathVariable UUID id) {
        return simulationService.getSimulationDetails(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    void createSimulation(@Valid @RequestBody CreateSimulationDto simulation) {
        simulationService.createSimulation(simulation);
    }

    @PutMapping("/{id}")
    void updateSimulationData(@PathVariable UUID id, @Valid @RequestBody CreateSimulationDto simulation) {
        simulationService.updateSimulation(id, simulation);
    }

    @DeleteMapping("/{id}")
    void deleteSimulation(@PathVariable UUID id) {
        simulationService.removeSimulation(id);
    }
}
