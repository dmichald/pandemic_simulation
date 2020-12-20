package com.md.pandemic_simulation.data.repository;

import com.md.pandemic_simulation.data.model.DaySummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DaySummaryRepository extends JpaRepository<DaySummary, UUID> {
}
