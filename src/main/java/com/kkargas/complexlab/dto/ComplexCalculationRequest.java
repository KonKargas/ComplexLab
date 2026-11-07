package com.kkargas.complexlab.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.kkargas.complexlab.enums.CalculationOperation;

public class ComplexCalculationRequest {

    @Valid
    @NotNull(message = "First complex number is required.")
    private ComplexNumberDTO first;

    @Valid
    @NotNull(message = "Second complex number is required.")
    private ComplexNumberDTO second;

    @NotNull(message = "Please select an operation.")
    private CalculationOperation operation;

    public ComplexCalculationRequest() {
        this.first = new ComplexNumberDTO();
        this.second = new ComplexNumberDTO();
    }

    public ComplexNumberDTO getFirst() {
        return first;
    }

    public void setFirst(ComplexNumberDTO first) {
        this.first = first;
    }

    public ComplexNumberDTO getSecond() {
        return second;
    }

    public void setSecond(ComplexNumberDTO second) {
        this.second = second;
    }

    public CalculationOperation getOperation() {
        return operation;
    }

    public void setOperation(CalculationOperation operation) {
        this.operation = operation;
    }
}
