package com.md.pandemic_simulation.web.generator;

import com.md.pandemic_simulation.data.model.DaySummary;
import com.md.pandemic_simulation.data.model.Simulation;

import java.util.List;

public interface PopulationGenerator {

    List<DaySummary> generate(Simulation simulation);
}
