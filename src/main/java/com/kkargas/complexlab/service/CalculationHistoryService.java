package com.kkargas.complexlab.service;

import com.kkargas.complexlab.model.CalculationHistory;
import com.kkargas.complexlab.model.CalculationHistory.Type;
import com.kkargas.complexlab.repository.CalculationHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalculationHistoryService {
    private final CalculationHistoryRepository repository;

    public CalculationHistoryService(CalculationHistoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CalculationHistory save(Type type, String operation,
                                   String inputA, String inputB, String result) {
        return repository.save(new CalculationHistory(type, operation, inputA, inputB, result));
    }

    @Transactional(readOnly = true)
    public Page<CalculationHistory> getHistory(Type type, int page) {
        int safePage = Math.max(0, page);
        var pageable = PageRequest.of(safePage, 20, Sort.by(Sort.Direction.DESC, "createdAt")
                                                      .and(Sort.by(Sort.Direction.DESC, "id")));
        return type == null ? repository.findAll(pageable)
                            : repository.findByCalculationType(type, pageable);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
