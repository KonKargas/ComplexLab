
package com.kkargas.complexlab.service;

import org.springframework.stereotype.Service;

import com.kkargas.complexlab.model.ComplexNumber;

@Service
public class ComplexNumberService {

    public ComplexNumber create(double real, double imaginary) {
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        return a.add(b);
    }

    public ComplexNumber subtract(ComplexNumber a, ComplexNumber b) {
        return a.subtract(b);
    }

    public ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
        return a.multiply(b);
    }

    public ComplexNumber divide(ComplexNumber a, ComplexNumber b) {
        return a.divide(b);
    }

    public ComplexNumber conjugate(ComplexNumber number) {
        return number.conjugate();
    }

    public double magnitude(ComplexNumber number) {
        return number.magnitude();
    }

    public double argumentRadians(ComplexNumber number) {
        return number.argument();
    }

    public double argumentDegrees(ComplexNumber number) {
        return Math.toDegrees(number.argument());
    }

    public ComplexNumber fromPolar(double magnitude, double angleRadians) {
        if (!Double.isFinite(magnitude)
                || !Double.isFinite(angleRadians)
                || magnitude < 0) {
            throw new IllegalArgumentException(
                "Invalid polar coordinates."
            );
        }

        return new ComplexNumber(
            magnitude * Math.cos(angleRadians),
            magnitude * Math.sin(angleRadians)
        );
    }
}
