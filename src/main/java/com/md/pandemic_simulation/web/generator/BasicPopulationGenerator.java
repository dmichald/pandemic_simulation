package com.md.pandemic_simulation.web.generator;

import com.md.pandemic_simulation.data.model.DaySummary;
import com.md.pandemic_simulation.data.model.Simulation;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BasicPopulationGenerator implements PopulationGenerator {
    private Map<DaySummary, DayExtraData> eachDayInformationMap = new HashMap<>();
    private List<DaySummary> days;
    private Simulation simulation;
    @Override
    public List<DaySummary> generate(Simulation simulation) {
        this.days = new ArrayList<>();
        this.simulation = simulation;

        return createSimulationDays();
    }

    private List<DaySummary> createSimulationDays() {
        days.add(firstDay(simulation));

        for (int i = 1; i < simulation.getTs(); i++) {
            DaySummary previous = days.get(i - 1);
            int newDied = getDied(i);
            int allDied = previous.getPm() + newDied;

            int newHealed = getNewHealed(i);
            int allHealed = previous.getPr() + newHealed;

            int newInfected = getNewInfected(i, newDied, newHealed);
            int allInfected = previous.getPi() - newDied - newHealed + newInfected;

            int healthy = simulation.getP() - allInfected - allDied - allHealed;


            DayExtraData dayExtraData = new DayExtraData(newInfected, newDied, newHealed);

            DaySummary daySummary = DaySummary.builder()
                    .day(i)
                    .Pi(allInfected)
                    .Pv(healthy)
                    .Pm(allDied)
                    .Pr(allHealed)
                    .simulation(simulation)
                    .build();

            days.add(daySummary);
            eachDayInformationMap.put(daySummary, dayExtraData);
        }
        return days;
    }

    private int getNewInfected(int iteration, int newDied, int newHealed) {
        DaySummary previous = days.get(iteration - 1);
        int infected = previous.getPi() - newDied - newHealed;
        int newInfected = simulation.getR() * infected;
        int healthyProneToInfection = previous.getPv();
        return Math.min(newInfected, healthyProneToInfection);
    }

    private int getAllInfected(DaySummary previous, int newDied, int newHealed, int newInfected) {
        int allInfected = previous.getPi() - newDied - newHealed + newInfected;
        if (infectedMoreThanHealthy(allInfected, previous.getPv())) {
            return previous.getPv();
        } else {
            return Math.max(allInfected, 0);
        }

    }

    private boolean infectedMoreThanHealthy(int infected, int healthy) {
        return infected >= healthy;
    }

    private int getNewInfected(DaySummary previous, int newDied, int newHealed, int newInfected) {
        int newI = previous.getPi() - newDied - newHealed;
        return Math.min(newI, newInfected);
    }


    private int getDied(int iteration) {
        int daysToDied = simulation.getTm();
        double M = simulation.getM();
        DaySummary previous = days.get(iteration - 1);
        if (!isAnybodyInfected(previous)) return 0;
        if (mayBeDie(iteration, daysToDied)) {
            DaySummary infectedDay = days.get(iteration - daysToDied);
            int infectedAtThisDay = getInfectedIn(infectedDay);
            int died = (int) (M * infectedAtThisDay);
            return Math.min(previous.getPi(), died);
        } else {
            return 0;
        }
    }

    private boolean mayBeDie(int currentIteration, int daysFromInfectedToDie) {
        return currentIteration >= daysFromInfectedToDie;
    }

    private int getNewHealed(int i) {
        int daysToDied = simulation.getTm();
        int daysToHeal = simulation.getTi();
        double mortalityIndicator = simulation.getM();

        DaySummary previous = days.get(i - 1);
        if (!isAnybodyInfected(previous)) return 0;
        if (mayBeHealed(i, daysToHeal)) {
            DaySummary infectedDay = days.get(i - daysToHeal);
            if (fasterHealThanDie(daysToHeal, daysToDied)) {
                return getInfectedIn(infectedDay);
            } else {
                int allInfected = getInfectedIn(infectedDay);
                DaySummary healDay = days.get(infectedDay.getDay() + simulation.getTm());

                int died = eachDayInformationMap.get(healDay).newDied;
                return allInfected - died;
            }
        } else {
            return 0;
        }
    }

    private boolean isAnybodyInfected(DaySummary previous) {
        return previous.getPi() != 0;
    }

    private boolean mayBeHealed(int currentIteration, int daysFromInfectedToHeal) {
        return currentIteration >= daysFromInfectedToHeal;
    }

    private boolean fasterHealThanDie(int daysFromInfectedToHealed, int daysFromInfectedToDied) {
        return daysFromInfectedToHealed < daysFromInfectedToDied;
    }

    private int getInfectedIn(DaySummary infectedDay) {
        return eachDayInformationMap.get(infectedDay).getNewInfected();
    }

    private DaySummary firstDay(Simulation simulation) {

        DaySummary first = DaySummary.builder()
                .Pi(simulation.getI())
                .Pv(simulation.getP() - simulation.getI())
                .Pm(0)
                .Pr(0)
                .day(0)
                .simulation(simulation)
                .build();

        DayExtraData dayExtraData = new DayExtraData(first.getPi(), first.getPm(), first.getPr());
        eachDayInformationMap.put(first, dayExtraData);
        return first;
    }
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
