package com.md.pandemic_simulation.data.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Integer day;

    /**
     * Amount of infected people
     */
    @NotNull
    @Min(0)
    private Integer Pi;

    /**
     * Amount of health people, but prone to infection
     */
    @NotNull
    @Min(0)
    private Integer Pv;

    /**
     * Amount of dead people
     */
    @NotNull
    @Min(0)
    private Integer Pm;

    /**
     * Amount of recovery people with earned immunity
     */
    @NotNull
    @Min(0)
    private Integer Pr;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_id")
    private Simulation simulation;
}
