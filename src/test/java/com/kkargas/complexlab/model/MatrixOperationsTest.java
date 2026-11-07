
package com.kkargas.complexlab.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MatrixOperationsTest {

    private static final double EPSILON = 1e-9;

    private ComplexMatrix createMatrix() {
        return new ComplexMatrix(new ComplexNumber[][] {
            {new ComplexNumber(1, 0), new ComplexNumber(2, 0)},
            {new ComplexNumber(3, 0), new ComplexNumber(4, 0)}
        });
    }

    private void assertComplexEquals(
            ComplexNumber expected, ComplexNumber actual) {

        assertEquals(expected.getReal(),
            actual.getReal(), EPSILON);

        assertEquals(expected.getImaginary(),
            actual.getImaginary(), EPSILON);
    }

    @Test
    void testIdentity() {
        ComplexMatrix result = MatrixOperations.identity(3);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(
                    new ComplexNumber(i == j ? 1 : 0, 0),
                    result.get(i, j)
                );
            }
        }
    }

    @Test
    void testTrace() {
        ComplexNumber result =
            MatrixOperations.trace(createMatrix());

        assertComplexEquals(
            new ComplexNumber(5, 0), result
        );
    }

    @Test
    void testDeterminant() {
        ComplexNumber result =
            MatrixOperations.determinant(createMatrix());

        assertComplexEquals(
            new ComplexNumber(-2, 0), result
        );
    }

    @Test
    void testComplexDeterminant() {
        ComplexMatrix matrix = new ComplexMatrix(
            new ComplexNumber[][] {
                {new ComplexNumber(1, 1),
                 new ComplexNumber(2, 0)},
                {new ComplexNumber(3, 0),
                 new ComplexNumber(4, -1)}
            }
        );

        assertComplexEquals(
            new ComplexNumber(-1, 3),
            MatrixOperations.determinant(matrix)
        );
    }

    @Test
    void testInverse() {
        ComplexMatrix inverse =
            MatrixOperations.inverse(createMatrix());

        assertComplexEquals(
            new ComplexNumber(-2, 0),
            inverse.get(0, 0)
        );

        assertComplexEquals(
            new ComplexNumber(1, 0),
            inverse.get(0, 1)
        );

        assertComplexEquals(
            new ComplexNumber(1.5, 0),
            inverse.get(1, 0)
        );

        assertComplexEquals(
            new ComplexNumber(-0.5, 0),
            inverse.get(1, 1)
        );
    }

    @Test
    void testInverseMultiplication() {
        ComplexMatrix matrix = createMatrix();
        ComplexMatrix inverse = MatrixOperations.inverse(matrix);

        ComplexMatrix product = matrix.multiply(inverse);
        ComplexMatrix identity = MatrixOperations.identity(2);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertComplexEquals(
                    identity.get(i, j), product.get(i, j)
                );
            }
        }
    }

    @Test
    void testSingularMatrix() {
        ComplexMatrix matrix = new ComplexMatrix(
            new ComplexNumber[][] {
                {new ComplexNumber(1, 0),
                 new ComplexNumber(2, 0)},
                {new ComplexNumber(2, 0),
                 new ComplexNumber(4, 0)}
            }
        );

        assertThrows(
            ArithmeticException.class,
            () -> MatrixOperations.inverse(matrix)
        );
    }

    @Test
    void testPower() {
        ComplexMatrix result =
            MatrixOperations.power(createMatrix(), 2);

        assertComplexEquals(
            new ComplexNumber(7, 0), result.get(0, 0)
        );

        assertComplexEquals(
            new ComplexNumber(10, 0), result.get(0, 1)
        );

        assertComplexEquals(
            new ComplexNumber(15, 0), result.get(1, 0)
        );

        assertComplexEquals(
            new ComplexNumber(22, 0), result.get(1, 1)
        );
    }

    @Test
    void testPowerZero() {
        ComplexMatrix result =
            MatrixOperations.power(createMatrix(), 0);

        ComplexMatrix identity = MatrixOperations.identity(2);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(identity.get(i, j), result.get(i, j));
            }
        }
    }

    @Test
    void testNonSquareMatrix() {
        ComplexMatrix matrix = new ComplexMatrix(2, 3);

        assertThrows(
            IllegalArgumentException.class,
            () -> MatrixOperations.determinant(matrix)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> MatrixOperations.inverse(matrix)
        );
    }
}
