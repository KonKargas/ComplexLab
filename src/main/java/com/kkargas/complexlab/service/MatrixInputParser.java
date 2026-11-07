
package com.kkargas.complexlab.service;

import org.springframework.stereotype.Service;

import com.kkargas.complexlab.model.ComplexMatrix;
import com.kkargas.complexlab.model.ComplexNumber;

@Service
public class MatrixInputParser {

    private static final int MAX_SIZE = 10;

    public ComplexMatrix parse(String input) {

        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(
                "Matrix cannot be empty."
            );
        }

        String[] lines = input.strip().split("\\R");

        if (lines.length > MAX_SIZE) {
            throw new IllegalArgumentException(
                "Maximum 10 matrix rows allowed."
            );
        }

        ComplexNumber[][] values = null;
        int columns = -1;

        for (int row = 0; row < lines.length; row++) {

            String[] cells = lines[row].trim().split("\\s+");

            if (columns == -1) {
                columns = cells.length;

                if (columns > MAX_SIZE) {
                    throw new IllegalArgumentException(
                        "Maximum 10 columns allowed."
                    );
                }

                values = new ComplexNumber[lines.length][columns];
            }

            if (cells.length != columns) {
                throw new IllegalArgumentException(
                    "All matrix rows must have equal length."
                );
            }

            for (int col = 0; col < columns; col++) {
                values[row][col] = parseNumber(cells[col]);
            }
        }

        return new ComplexMatrix(values);
    }

    private ComplexNumber parseNumber(String value) {

        String[] parts = value.split(":", -1);

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                "Invalid element '" + value
                + "'. Use real:imaginary format."
            );
        }

        try {
            double real = Double.parseDouble(parts[0]);
            double imaginary = Double.parseDouble(parts[1]);

            return new ComplexNumber(real, imaginary);

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                "Invalid numeric value: " + value
            );
        }
    }
}
