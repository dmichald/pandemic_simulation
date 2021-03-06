package com.md.pandemic_simulation.web.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class SimulationDto {
    private UUID id;
    private String N;
    private Integer P;
    private Integer I;
    private Integer R;
    private Double M;
    private Integer Ti;
    private Integer Tm;
    private Integer Ts;
}
