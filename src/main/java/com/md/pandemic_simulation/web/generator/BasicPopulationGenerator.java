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

    @Override
    public List<DaySummary> generate(Simulation simulation) {
        return createSimulationDays(simulation);
    }

    private List<DaySummary> createSimulationDays(Simulation simulation) {
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
            int allInfected = getAllInfected(previous, newDied, newHealed, newInfected);


            //INFECTED
            int healthy = simulation.getP() - allInfected - allDied - allHealed;

              dayExtraData.setNewDied(newDied);
              dayExtraData.setHealed(newHealed);
            int newI = previous.getPi() - newDied - newHealed;
            int dd = Math.min(newI, newInfected);
            dayExtraData.setNewInfected(dd);
            DayExtraData extras = new DayExtraData(dd, newDied, newHealed);

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


    private int getDied(int iteration, List<DaySummary> days, int daysToDied, double M) {
        DaySummary previous = days.get(iteration - 1);
        if (!isAnybodyInfected(previous)) return 0;
        if (mayBeDie(iteration, daysToDied)) {
            DaySummary infectedDay = days.get(iteration - daysToDied);
            int infectedAtThisDay = getInfectedIn(infectedDay);
            return (int) (M * infectedAtThisDay);
        } else {
            return 0;
        }
    }

    private boolean mayBeDie(int currentIteration, int daysFromInfectedToDie) {
        return currentIteration >= daysFromInfectedToDie;
    }

    private int getNewHealed(int i, List<DaySummary> days, int daysToDied, int daysToHeal, double mortalityIndicator) {
        DaySummary previous = days.get(i - 1);
        if (!isAnybodyInfected(previous)) return 0;
        if (mayBeHealed(i, daysToHeal)) {
            DaySummary infectedDay = days.get(i - daysToHeal);
            if (fasterHealThanDie(daysToHeal, daysToDied)) {
                return getInfectedIn(infectedDay);
            } else {
                int allInfected = getInfectedIn(infectedDay);
                int died = (int) (allInfected * mortalityIndicator);
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
