
package com.kkargas.complexlab.model;

import java.util.Objects;

public final class MatrixOperations {

    private MatrixOperations() {
        // Utility class: no instances allowed
    }

    // Creates an identity matrix
    public static ComplexMatrix identity(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(
                "Identity matrix size must be positive."
            );
        }

        ComplexNumber[][] values =
            new ComplexNumber[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                values[i][j] = new ComplexNumber(
                    i == j ? 1 : 0, 0
                );
            }
        }

        return new ComplexMatrix(values);
    }

    // Computes the trace
    public static ComplexNumber trace(ComplexMatrix matrix) {
        validateSquare(matrix);

        ComplexNumber sum = new ComplexNumber(0, 0);

        for (int i = 0; i < matrix.getRows(); i++) {
            sum = sum.add(matrix.get(i, i));
        }

        return sum;
    }

    // Computes the determinant using Gaussian elimination
    public static ComplexNumber determinant(ComplexMatrix matrix) {
        validateSquare(matrix);

        int n = matrix.getRows();
        ComplexNumber[][] a = toArray(matrix);
        ComplexNumber determinant = new ComplexNumber(1, 0);
        int swaps = 0;

        for (int col = 0; col < n; col++) {

            int pivot = findPivot(a, col);

            if (isZero(a[pivot][col])) {
                return new ComplexNumber(0, 0);
            }

            if (pivot != col) {
                swapRows(a, pivot, col);
                swaps++;
            }

            ComplexNumber pivotValue = a[col][col];
            determinant = determinant.multiply(pivotValue);

            for (int row = col + 1; row < n; row++) {
                ComplexNumber factor =
                    a[row][col].divide(pivotValue);

                a[row][col] = new ComplexNumber(0, 0);

                for (int j = col + 1; j < n; j++) {
                    a[row][j] = a[row][j].subtract(
                        factor.multiply(a[col][j])
                    );
                }
            }
        }

        return swaps % 2 == 0
            ? determinant
            : determinant.multiply(new ComplexNumber(-1, 0));
    }

    // Computes the inverse using Gauss-Jordan elimination
    public static ComplexMatrix inverse(ComplexMatrix matrix) {
        validateSquare(matrix);

        int n = matrix.getRows();
        ComplexNumber[][] a = toArray(matrix);
        ComplexNumber[][] inverse =
            toArray(identity(n));

        for (int col = 0; col < n; col++) {

            int pivot = findPivot(a, col);

            if (isZero(a[pivot][col])) {
                throw new ArithmeticException(
                    "Matrix is singular and cannot be inverted."
                );
            }

            if (pivot != col) {
                swapRows(a, pivot, col);
                swapRows(inverse, pivot, col);
            }

            ComplexNumber pivotValue = a[col][col];

            // Normalize pivot row
            for (int j = 0; j < n; j++) {
                a[col][j] = a[col][j].divide(pivotValue);
                inverse[col][j] =
                    inverse[col][j].divide(pivotValue);
            }

            // Eliminate all other rows
            for (int row = 0; row < n; row++) {
                if (row == col) {
                    continue;
                }

                ComplexNumber factor = a[row][col];

                for (int j = 0; j < n; j++) {
                    a[row][j] = a[row][j].subtract(
                        factor.multiply(a[col][j])
                    );

                    inverse[row][j] =
                        inverse[row][j].subtract(
                            factor.multiply(inverse[col][j])
                        );
                }
            }
        }

        return new ComplexMatrix(inverse);
    }

    // Raises a matrix to a non-negative integer power
    public static ComplexMatrix power(
            ComplexMatrix matrix, int exponent) {

        validateSquare(matrix);

        if (exponent < 0) {
            throw new IllegalArgumentException(
                "Exponent cannot be negative."
            );
        }

        ComplexMatrix result = identity(matrix.getRows());
        ComplexMatrix base = matrix;
        int remaining = exponent;

        // Exponentiation by squaring
        while (remaining > 0) {

            if ((remaining & 1) == 1) {
                result = result.multiply(base);
            }

            remaining >>= 1;

            if (remaining > 0) {
                base = base.multiply(base);
            }
        }

        return result;
    }

    private static void validateSquare(ComplexMatrix matrix) {
        Objects.requireNonNull(matrix, "Matrix cannot be null");

        if (matrix.getRows() != matrix.getCols()) {
            throw new IllegalArgumentException(
                "Operation requires a square matrix."
            );
        }
    }

    private static ComplexNumber[][] toArray(
            ComplexMatrix matrix) {

        int rows = matrix.getRows();
        int cols = matrix.getCols();

        ComplexNumber[][] result =
            new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix.get(i, j);
            }
        }

        return result;
    }

    private static int findPivot(
            ComplexNumber[][] a, int col) {

        int pivot = col;
        double max = a[col][col].magnitude();

        for (int row = col + 1; row < a.length; row++) {
            double magnitude = a[row][col].magnitude();

            if (magnitude > max) {
                max = magnitude;
                pivot = row;
            }
        }

        return pivot;
    }

    private static void swapRows(
            ComplexNumber[][] matrix, int a, int b) {

        ComplexNumber[] temp = matrix[a];
        matrix[a] = matrix[b];
        matrix[b] = temp;
    }

    private static boolean isZero(ComplexNumber number) {
        return number.getReal() == 0.0
            && number.getImaginary() == 0.0;
    }
}
