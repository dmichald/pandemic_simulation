package com.md.pandemic_simulation.data.model;

import com.md.pandemic_simulation.web.validators.SmallerThanPopulation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author Michal Dyngosz
 * Class contains information about simulation.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@SmallerThanPopulation
public class Simulation implements Serializable {

    @Id
    @GeneratedValue
    UUID id;

    /**
     * Simulation name
     */
    @NotNull
    @NotEmpty
    @NotBlank
    private String N;

    /**
     * Amount of people in population
     */
    @Min(0)
    private Integer P;

    /**
     * Initial number of infected people
     */

    @Min(0)
    private Integer I;


    /**
     * Indicator - points how many people can be infected by one infected person
     */
    @Min(0)
    private Integer R;

    /**
     * Mortality indicator - points how many infected people die
     */
    @Min(0)
    @Max(1)
    private Double M;

    /**
     * Time from infected to recover in days
     */
    @Min(0)
    private Integer Ti;

    /**
     * Time from infected to death in days
     */
    @Min(0)
    private Integer Tm;

    /**
     * Simulation duration in days
     */
    @Min(0)
    private Integer Ts;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "simulation")
    private List<DaySummary> epidemicDays = new ArrayList<>();

}
