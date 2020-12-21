package com.md.pandemic_simulation.web.dto;

import com.md.pandemic_simulation.web.validators.InfectedLessThanPopulation;
import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor

@InfectedLessThanPopulation
public class CreateSimulationDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String N;

    @Min(0)
    private Integer P;

    @Min(0)
    private Integer I;

    @Min(0)
    private Integer R;

    @Min(0)
    @Max(1)
    private Double M;

    @Min(0)
    private Integer Ti;

    @Min(0)
    private Integer Tm;

    @Min(0)
    private Integer Ts;

}
