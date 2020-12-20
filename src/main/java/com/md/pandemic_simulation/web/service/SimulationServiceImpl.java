package com.md.pandemic_simulation.web.service;

import com.md.pandemic_simulation.data.model.DaySummary;
import com.md.pandemic_simulation.data.model.Simulation;
import com.md.pandemic_simulation.data.model.SimulationName;
import com.md.pandemic_simulation.web.dto.CreateSimulationDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimulationServiceImpl implements SimulationService {
    private Map<DaySummary, DayExtraData> extraDataMap = new HashMap<>();
    //  private final SimulationRepository simulationRepository;
    //   private final DaySummaryRepository daySummaryRepository;

    @Override
    public void createSimulation(CreateSimulationDto simulationDto) {
        Simulation createdSimulation = simulation(simulationDto);

    }

    private Simulation simulation(CreateSimulationDto simulationDto) {
        return Simulation.builder()
                .N(simulationDto.getName())
                .P(simulationDto.getPopulation())
                .I(simulationDto.getAmountOfInfectedPeople())
                .R(simulationDto.getRIndicator())
                .M(simulationDto.getMortalityIndicator())
                .Ti(simulationDto.getDaysFromInfectedToRecover())
                .Tm(simulationDto.getDaysFromInfectedToDie())
                .Ts(simulationDto.getSimulationDurationInDays())
                .build();
    }

    public static void main(String[] args) {
        SimulationServiceImpl simulationService = new SimulationServiceImpl();
        Simulation simulation = Simulation.builder()
                .N("moja symulacja")
                .P(100)
                .I(20)
                .R(1)
                .M(0.0)
                .Ti(2)
                .Tm(1)
                .Ts(58)
                .build();
        simulationService.createSimulationDays(simulation);

    }

    void createSimulationDays(Simulation simulation) {
        List<DaySummary> days = new ArrayList<>();
        days.add(firstDay(simulation));

        for (int i = 1; i < simulation.getTs(); i++) {
            DayExtraData dayExtraData = new DayExtraData();
            DaySummary previous = days.get(i - 1);

            //died
            int newDied = getDied(i, days, simulation.getTm(), simulation.getM());
            dayExtraData.setNewDied(newDied);

            int allDied = previous.getPm() + newDied;
            //end died

            //healed
            int newHealed = getNewHealed(i, days, simulation.getTm(), simulation.getTi(), simulation.getM());
            int allHealed = previous.getPr() + newHealed;
            //end healed

            //INFECTED
            int newInfected = (previous.getPi() - newDied - newHealed) * simulation.getR();
            int allInfected = previous.getPi() - newDied - newHealed + newInfected;
            int correctInfected;
            if (allInfected >= previous.getPv()) {
                correctInfected = previous.getPv();
            } else if(allInfected <= 0){
                //trim
                correctInfected = 0;
            }else {
                correctInfected = allInfected;
            }
            //INFECTED
            int healthy = simulation.getP() - correctInfected - allDied - allHealed;

            dayExtraData.setNewDied(newDied);
            dayExtraData.setHealed(newHealed);
            int a = previous.getPi() - newDied - newHealed;
            int dd = Math.min(a, newInfected);
            dayExtraData.setNewInfected(dd);

            DaySummary daySummary = DaySummary.builder()
                    .day(i)
                    .Pi(correctInfected)
                    .Pv(healthy)
                    .Pm(allDied)
                    .Pr(allHealed)
                    .build();

            days.add(daySummary);
            extraDataMap.put(daySummary, dayExtraData);
        }

        days.forEach(daySummary -> System.out.println(daySummary.toString()));
        days.forEach(daySummary -> System.out.println(daySummary.getPv() + daySummary.getPi() + daySummary.getPm() + daySummary.getPr()));
        extraDataMap.values().forEach(System.out::println);
    }

    int getDied(int iteration, List<DaySummary> days, int daysToDied, double M) {
        DaySummary previous = days.get(iteration -1);
        if(previous.getPi() == 0) return 0;
        if (iteration >= daysToDied) {
            DaySummary infectedDay = days.get(iteration - daysToDied );
            int infectedAtThisDay = extraDataMap.get(infectedDay).getNewInfected();
            return (int) (M * infectedAtThisDay);
        } else {
            return 0;
        }
    }

    int getNewHealed(int i, List<DaySummary> days, int daysToDied, int daysToHeal, double M) {
        DaySummary previous = days.get(i -1);
        if(previous.getPi() == 0) return 0;
        if (i >= daysToHeal) {
            DaySummary infectedDay = days.get(i - daysToHeal );
            if (daysToHeal < daysToDied) {
                return extraDataMap.get(infectedDay).getNewInfected();
            } else {
                int allInfected = extraDataMap.get(infectedDay).getNewInfected();
                int died = (int) (allInfected * M);
                return allInfected - died;
            }
        } else {
            return 0;
        }
    }

    private DaySummary firstDay(Simulation simulation) {

        DaySummary first = DaySummary.builder()
                .Pi(simulation.getI())
                .Pv(simulation.getP() - simulation.getI())
                .Pm(0)
                .Pr(0)
                .day(0)
                .build();
        DayExtraData dayExtraData = new DayExtraData(first.getPi(), first.getPm(), first.getPr());
        extraDataMap.put(first, dayExtraData);
        return first;
    }

    @Override
    public SimulationName getSimulations() {
        return null;
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    class DayExtraData {
        int newInfected;
        int newDied;
        int healed;
    }
}
