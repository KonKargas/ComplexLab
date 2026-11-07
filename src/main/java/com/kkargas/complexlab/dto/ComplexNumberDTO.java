package com.kkargas.complexlab.dto;

import jakarta.validation.constraints.NotNull;

import com.kkargas.complexlab.model.ComplexNumber;

public class ComplexNumberDTO {

    @NotNull(message = "Real part is required.")
    private Double real;

    @NotNull(message = "Imaginary part is required.")
    private Double imaginary;

    public ComplexNumberDTO() {
    }

    public ComplexNumberDTO(Double real, Double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Double getReal() {
        return real;
    }

    public void setReal(Double real) {
        this.real = real;
    }

    public Double getImaginary() {
        return imaginary;
    }

    public void setImaginary(Double imaginary) {
        this.imaginary = imaginary;
    }

    public ComplexNumber toModel() {
        if (real == null || imaginary == null) {
            throw new IllegalArgumentException(
                "Both complex number components are required."
            );
        }

        return new ComplexNumber(real, imaginary);
    }

    public static ComplexNumberDTO fromModel(ComplexNumber number) {
        return new ComplexNumberDTO(
            number.getReal(),
            number.getImaginary()
        );
    }
}
