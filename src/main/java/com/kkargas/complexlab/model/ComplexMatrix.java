
package com.kkargas.complexlab.model;

import java.util.Objects;
import java.util.Random;

public final class ComplexMatrix {

    private final int rows;
    private final int cols;
    private final ComplexNumber[][] data;

    // Creates a zero-filled matrix
    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException(
                "Matrix dimensions must be positive."
            );
        }

        this.rows = rows;
        this.cols = cols;
        this.data = new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new ComplexNumber(0, 0);
            }
        }
    }

    // Creates a matrix from existing values
    public ComplexMatrix(ComplexNumber[][] values) {
        Objects.requireNonNull(values, "Values cannot be null");

        if (values.length == 0 || values[0] == null
                || values[0].length == 0) {
            throw new IllegalArgumentException(
                "Matrix cannot be empty."
            );
        }

        this.rows = values.length;
        this.cols = values[0].length;
        this.data = new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            if (values[i] == null || values[i].length != cols) {
                throw new IllegalArgumentException(
                    "Matrix rows must have equal length."
                );
            }

            for (int j = 0; j < cols; j++) {
                data[i][j] = Objects.requireNonNull(
                    values[i][j],
                    "Matrix elements cannot be null."
                );
            }
        }
    }

    // Copy constructor
    public ComplexMatrix(ComplexMatrix original) {
        this(Objects.requireNonNull(original).data);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ComplexNumber get(int row, int col) {
        checkIndex(row, col);
        return data[row][col];
    }

    private void checkIndex(int row, int col) {
        if (row < 0 || row >= rows ||
                col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException(
                "Invalid matrix position."
            );
        }
    }

    // Returns a new matrix with one changed element
    public ComplexMatrix withElement(
            int row, int col, ComplexNumber value) {

        checkIndex(row, col);
        Objects.requireNonNull(value);

        ComplexNumber[][] result = copyData();
        result[row][col] = value;

        return new ComplexMatrix(result);
    }

    private ComplexNumber[][] copyData() {
        ComplexNumber[][] copy =
            new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, copy[i], 0, cols);
        }

        return copy;
    }

    // Addition
    public ComplexMatrix add(ComplexMatrix other) {
        validateSameDimensions(other);

        ComplexNumber[][] result =
            new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] =
                    data[i][j].add(other.data[i][j]);
            }
        }

        return new ComplexMatrix(result);
    }

    // Subtraction
    public ComplexMatrix subtract(ComplexMatrix other) {
        validateSameDimensions(other);

        ComplexNumber[][] result =
            new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] =
                    data[i][j].subtract(other.data[i][j]);
            }
        }

        return new ComplexMatrix(result);
    }

    // Matrix multiplication
    public ComplexMatrix multiply(ComplexMatrix other) {
        Objects.requireNonNull(other);

        if (cols != other.rows) {
            throw new IllegalArgumentException(
                "Incompatible dimensions for multiplication."
            );
        }

        ComplexNumber[][] result =
            new ComplexNumber[rows][other.cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {

                ComplexNumber sum = new ComplexNumber(0, 0);

                for (int k = 0; k < cols; k++) {
                    sum = sum.add(
                        data[i][k].multiply(other.data[k][j])
                    );
                }

                result[i][j] = sum;
            }
        }

        return new ComplexMatrix(result);
    }

    // Scalar multiplication
    public ComplexMatrix multiply(ComplexNumber scalar) {
        Objects.requireNonNull(scalar);

        ComplexNumber[][] result =
            new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = data[i][j].multiply(scalar);
            }
        }

        return new ComplexMatrix(result);
    }

    // Transpose
    public ComplexMatrix transpose() {
        ComplexNumber[][] result =
            new ComplexNumber[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = data[i][j];
            }
        }

        return new ComplexMatrix(result);
    }

    // Conjugate transpose
    public ComplexMatrix conjugateTranspose() {
        ComplexNumber[][] result =
            new ComplexNumber[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = data[i][j].conjugate();
            }
        }

        return new ComplexMatrix(result);
    }

    private void validateSameDimensions(ComplexMatrix other) {
        Objects.requireNonNull(other);

        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException(
                "Matrices must have the same dimensions."
            );
        }
    }

    // Generates a random matrix
    public static ComplexMatrix random(
            int rows, int cols, double min, double max) {

        if (rows <= 0 || cols <= 0 ||
                !Double.isFinite(min) ||
                !Double.isFinite(max) || min >= max) {
            throw new IllegalArgumentException(
                "Invalid dimensions or random range."
            );
        }

        Random random = new Random();
        ComplexNumber[][] values =
            new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double real =
                    min + random.nextDouble() * (max - min);

                double imaginary =
                    min + random.nextDouble() * (max - min);

                values[i][j] =
                    new ComplexNumber(real, imaginary);
            }
        }

        return new ComplexMatrix(values);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < rows; i++) {
            if (i > 0) {
                sb.append(System.lineSeparator()).append(" ");
            }

            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]);

                if (j < cols - 1) {
                    sb.append(", ");
                }
            }

            if (i < rows - 1) {
                sb.append(";");
            }
        }

        return sb.append("]").toString();
    }
}
