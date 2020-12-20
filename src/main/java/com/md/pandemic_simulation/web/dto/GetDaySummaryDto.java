package com.md.pandemic_simulation.web.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GetDaySummaryDto {
    private UUID id;
    private Integer day;
    private Integer infectedPeople;
    private Integer healthyPeopleProneToInfection;
    private Integer diedPeople;
    private Integer healedPeopleWithImmunity;
}
