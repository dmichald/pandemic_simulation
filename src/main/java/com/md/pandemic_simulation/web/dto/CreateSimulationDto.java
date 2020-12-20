package com.md.pandemic_simulation.web.dto;

import lombok.Data;

@Data
public class CreateSimulationDto {
    private String name;
    private Integer population;
    private Integer amountOfInfectedPeople;
    private Integer RIndicator;
    private Double mortalityIndicator;
    private Integer daysFromInfectedToRecover;
    private Integer daysFromInfectedToDie;
    private Integer simulationDurationInDays;

}
