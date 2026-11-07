package com.kkargas.complexlab.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calculation_history", indexes = {
    @Index(name = "idx_calculation_history_created", columnList = "created_at"),
    @Index(name = "idx_calculation_history_type", columnList = "calculation_type")
})
public class CalculationHistory {

    public enum Type { COMPLEX_NUMBER, MATRIX }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "calculation_type", nullable = false, length = 32)
    private Type calculationType;

    @Column(nullable = false, length = 64)
    private String operation;

    @Column(name = "input_a", nullable = false, columnDefinition = "TEXT")
    private String inputA;

    @Column(name = "input_b", columnDefinition = "TEXT")
    private String inputB;

    @Column(name = "result_value", nullable = false, columnDefinition = "TEXT")
    private String result;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected CalculationHistory() { }

    public CalculationHistory(Type type, String operation, String inputA,
                              String inputB, String result) {
        this.calculationType = type;
        this.operation = operation;
        this.inputA = inputA;
        this.inputB = inputB;
        this.result = result;
    }

    @PrePersist
    void created() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Type getCalculationType() { return calculationType; }
    public String getOperation() { return operation; }
    public String getInputA() { return inputA; }
    public String getInputB() { return inputB; }
    public String getResult() { return result; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
