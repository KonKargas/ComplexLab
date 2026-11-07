package com.kkargas.complexlab.dto;

import com.kkargas.complexlab.enums.MatrixOperation;
import jakarta.validation.constraints.NotNull;

public class MatrixCalculationRequest {
    private String matrixA = "1:2 3:0\n2:-1 4:3";
    private String matrixB = "2:0 1:1\n0:-2 3:0";
    @NotNull(message = "Select an operation.")
    private MatrixOperation operation = MatrixOperation.ADD;
    private Integer exponent = 2;
    private Double scalarReal = 2.0;
    private Double scalarImaginary = 0.0;
    private Double randomMin = -10.0;
    private Double randomMax = 10.0;

    public String getMatrixA() { return matrixA; }
    public void setMatrixA(String matrixA) { this.matrixA = matrixA; }
    public String getMatrixB() { return matrixB; }
    public void setMatrixB(String matrixB) { this.matrixB = matrixB; }
    public MatrixOperation getOperation() { return operation; }
    public void setOperation(MatrixOperation operation) { this.operation = operation; }
    public Integer getExponent() { return exponent; }
    public void setExponent(Integer exponent) { this.exponent = exponent; }
    public Double getScalarReal() { return scalarReal; }
    public void setScalarReal(Double scalarReal) { this.scalarReal = scalarReal; }
    public Double getScalarImaginary() { return scalarImaginary; }
    public void setScalarImaginary(Double scalarImaginary) { this.scalarImaginary = scalarImaginary; }
    public Double getRandomMin() { return randomMin; }
    public void setRandomMin(Double randomMin) { this.randomMin = randomMin; }
    public Double getRandomMax() { return randomMax; }
    public void setRandomMax(Double randomMax) { this.randomMax = randomMax; }
}
