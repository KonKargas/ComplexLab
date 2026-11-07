
package com.kkargas.complexlab.service;

import org.springframework.stereotype.Service;

import com.kkargas.complexlab.model.ComplexMatrix;
import com.kkargas.complexlab.model.ComplexNumber;
import com.kkargas.complexlab.model.MatrixOperations;

@Service
public class ComplexMatrixService {

    public ComplexMatrix create(int rows, int cols) {
        return new ComplexMatrix(rows, cols);
    }

    public ComplexMatrix create(ComplexNumber[][] values) {
        return new ComplexMatrix(values);
    }

    public ComplexMatrix random(
            int rows, int cols, double min, double max) {

        return ComplexMatrix.random(rows, cols, min, max);
    }

    public ComplexMatrix add(ComplexMatrix a, ComplexMatrix b) {
        return a.add(b);
    }

    public ComplexMatrix subtract(ComplexMatrix a, ComplexMatrix b) {
        return a.subtract(b);
    }

    public ComplexMatrix multiply(ComplexMatrix a, ComplexMatrix b) {
        return a.multiply(b);
    }

    public ComplexMatrix scalarMultiply(
            ComplexMatrix matrix, ComplexNumber scalar) {

        return matrix.multiply(scalar);
    }

    public ComplexMatrix transpose(ComplexMatrix matrix) {
        return matrix.transpose();
    }

    public ComplexMatrix conjugateTranspose(ComplexMatrix matrix) {
        return matrix.conjugateTranspose();
    }

    public ComplexNumber determinant(ComplexMatrix matrix) {
        return MatrixOperations.determinant(matrix);
    }

    public ComplexNumber trace(ComplexMatrix matrix) {
        return MatrixOperations.trace(matrix);
    }

    public ComplexMatrix inverse(ComplexMatrix matrix) {
        return MatrixOperations.inverse(matrix);
    }

    public ComplexMatrix identity(int size) {
        return MatrixOperations.identity(size);
    }

    public ComplexMatrix power(ComplexMatrix matrix, int exponent) {
        return MatrixOperations.power(matrix, exponent);
    }
}
