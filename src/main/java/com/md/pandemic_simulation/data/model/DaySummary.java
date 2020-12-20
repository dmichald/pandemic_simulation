package com.md.pandemic_simulation.data.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

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
    private Integer day;
    private Integer Pi; // number of infected people
    private Integer Pv; // number of health people, but prone to infections
    private Integer Pm; // number of dead people
    private Integer Pr; // number of recovery people with immunity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_id")
    private Simulation simulation;
}
