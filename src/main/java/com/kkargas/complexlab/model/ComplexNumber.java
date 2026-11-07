
package com.kkargas.complexlab.model;

import java.util.Objects;

public final class ComplexNumber {

    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        if (!Double.isFinite(real) || !Double.isFinite(imaginary)) {
            throw new IllegalArgumentException(
                "Complex number components must be finite."
            );
        }

        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    // Addition
    public ComplexNumber add(ComplexNumber other) {
        Objects.requireNonNull(other, "Other number cannot be null");

        return new ComplexNumber(
            real + other.real,
            imaginary + other.imaginary
        );
    }

    // Subtraction
    public ComplexNumber subtract(ComplexNumber other) {
        Objects.requireNonNull(other, "Other number cannot be null");

        return new ComplexNumber(
            real - other.real,
            imaginary - other.imaginary
        );
    }

    // Multiplication
    public ComplexNumber multiply(ComplexNumber other) {
        Objects.requireNonNull(other, "Other number cannot be null");

        double newReal =
            real * other.real - imaginary * other.imaginary;

        double newImaginary =
            real * other.imaginary + imaginary * other.real;

        return new ComplexNumber(newReal, newImaginary);
    }

    // Division
    public ComplexNumber divide(ComplexNumber other) {
        Objects.requireNonNull(other, "Other number cannot be null");

        double a = real;
        double b = imaginary;
        double c = other.real;
        double d = other.imaginary;

        if (c == 0.0 && d == 0.0) {
            throw new ArithmeticException("Division by zero.");
        }

        double newReal;
        double newImaginary;

        // Scaled division to improve numerical stability
        if (Math.abs(c) >= Math.abs(d)) {
            double ratio = d / c;
            double denominator = c + d * ratio;

            newReal = (a + b * ratio) / denominator;
            newImaginary = (b - a * ratio) / denominator;
        } else {
            double ratio = c / d;
            double denominator = d + c * ratio;

            newReal = (a * ratio + b) / denominator;
            newImaginary = (b * ratio - a) / denominator;
        }

        return new ComplexNumber(newReal, newImaginary);
    }

    // Complex conjugate
    public ComplexNumber conjugate() {
        return new ComplexNumber(real, -imaginary);
    }

    // Magnitude
    public double magnitude() {
        return Math.hypot(real, imaginary);
    }

    // Argument in radians
    public double argument() {
        return Math.atan2(imaginary, real);
    }

    @Override
    public String toString() {
        String sign = imaginary < 0 ? " - " : " + ";

        return real + sign + Math.abs(imaginary) + "i";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ComplexNumber other)) {
            return false;
        }

        return real == other.real
            && imaginary == other.imaginary;
    }

    @Override
    public int hashCode() {
        double normalizedReal = real == 0.0 ? 0.0 : real;
        double normalizedImaginary =
            imaginary == 0.0 ? 0.0 : imaginary;

        return Objects.hash(normalizedReal, normalizedImaginary);
    }
}
