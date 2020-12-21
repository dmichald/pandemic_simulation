package com.md.pandemic_simulation.web.mapper;

import com.md.pandemic_simulation.data.model.DaySummary;
import com.md.pandemic_simulation.web.dto.GetDaySummaryDto;
import org.mapstruct.Mapper;

@Mapper
public interface DaySummaryMapper {
    GetDaySummaryDto mapToGetSummaryDto(DaySummary daySummary);
}
