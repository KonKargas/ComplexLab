package com.kkargas.complexlab.repository;

import com.kkargas.complexlab.model.CalculationHistory;
import com.kkargas.complexlab.model.CalculationHistory.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationHistoryRepository extends JpaRepository<CalculationHistory, Long> {
    Page<CalculationHistory> findByCalculationType(Type calculationType, Pageable pageable);
}
