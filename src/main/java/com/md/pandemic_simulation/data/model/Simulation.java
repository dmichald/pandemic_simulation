package com.md.pandemic_simulation.data.model;

import com.md.pandemic_simulation.web.validators.SmallerThanPopulation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Simulation implements Serializable {

    @Id
    @GeneratedValue
    UUID id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String N; //name
    @Min(0)
    private Integer P;   //population

    @SmallerThanPopulation()
    @Min(0)   //initial number of infected people
    private Integer I;

    @Min(0)
    private Integer R; //R indicator

    @Min(0) @Max(1) //mortality indicator
    private Double M;
    @Min(0)
    private Integer Ti;  //elapsed days from infected to recover

    @Min(0)
    private Integer Tm; //elapsed days from infected to death

    @Min(0)
    private Integer Ts; //simulation duration in days

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "simulation")
    private Set<DaySummary> epidemicDays = new HashSet<>();

}
