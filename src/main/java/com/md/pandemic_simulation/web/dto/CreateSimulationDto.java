package com.md.pandemic_simulation.web.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
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
