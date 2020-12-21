package com.md.pandemic_simulation;

import com.md.pandemic_simulation.web.service.SimulationServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PandemicSimulationApplication {

    public static void main(String[] args) {
       var context =  SpringApplication.run(PandemicSimulationApplication.class, args);
        context.getBean(SimulationServiceImpl.class).createSimulation(null);
    }

}
