package com.md.pandemic_simulation.web.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class GetDaySummaryDto {
    private UUID id;
    private Integer day;
    private Integer Pi;
    private Integer Pv;
    private Integer Pm;
    private Integer Pr;
}
