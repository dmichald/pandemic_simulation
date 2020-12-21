package com.md.pandemic_simulation.data.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Michal Dyngosz
 * This class contains information about each single day in epidemic.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class DaySummary implements Serializable {
    @Id
    @GeneratedValue
    UUID id;
    /**
     * Day od epidemic. Starts from 0
     */
    private Integer day;

    /**
     * Amount of infected people
     */
    private Integer Pi;

    /**
     * Amount of health people, but prone to infection
     */
    private Integer Pv;

    /**
     * Amount of dead people
     */
    private Integer Pm;

    /**
     * Amount of recovery people with earned immunity
     */
    private Integer Pr;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_id")
    private Simulation simulation;
}
