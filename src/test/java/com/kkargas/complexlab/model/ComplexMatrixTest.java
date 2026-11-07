
package com.kkargas.complexlab.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexMatrixTest {

    private ComplexMatrix createMatrixA() {
        return new ComplexMatrix(new ComplexNumber[][] {
            { new ComplexNumber(1, 0), new ComplexNumber(2, 0) },
            { new ComplexNumber(3, 0), new ComplexNumber(4, 0) }
        });
    }

    private ComplexMatrix createMatrixB() {
        return new ComplexMatrix(new ComplexNumber[][] {
            { new ComplexNumber(5, 0), new ComplexNumber(6, 0) },
            { new ComplexNumber(7, 0), new ComplexNumber(8, 0) }
        });
    }

    @Test
    void testCreation() {
        ComplexMatrix matrix = new ComplexMatrix(3, 4);

        assertEquals(3, matrix.getRows());
        assertEquals(4, matrix.getCols());
        assertEquals(new ComplexNumber(0, 0), matrix.get(0, 0));
    }

    @Test
    void testAddition() {
        ComplexMatrix result = createMatrixA().add(createMatrixB());

        assertEquals(new ComplexNumber(6, 0), result.get(0, 0));
        assertEquals(new ComplexNumber(8, 0), result.get(0, 1));
        assertEquals(new ComplexNumber(10, 0), result.get(1, 0));
        assertEquals(new ComplexNumber(12, 0), result.get(1, 1));
    }

    @Test
    void testSubtraction() {
        ComplexMatrix result =
            createMatrixB().subtract(createMatrixA());

        assertEquals(new ComplexNumber(4, 0), result.get(0, 0));
        assertEquals(new ComplexNumber(4, 0), result.get(1, 1));
    }

    @Test
    void testMultiplication() {
        ComplexMatrix result =
            createMatrixA().multiply(createMatrixB());

        assertEquals(new ComplexNumber(19, 0), result.get(0, 0));
        assertEquals(new ComplexNumber(22, 0), result.get(0, 1));
        assertEquals(new ComplexNumber(43, 0), result.get(1, 0));
        assertEquals(new ComplexNumber(50, 0), result.get(1, 1));
    }

    @Test
    void testTranspose() {
        ComplexMatrix result = createMatrixA().transpose();

        assertEquals(new ComplexNumber(3, 0), result.get(0, 1));
        assertEquals(new ComplexNumber(2, 0), result.get(1, 0));
    }

    @Test
    void testConjugateTranspose() {
        ComplexMatrix matrix = new ComplexMatrix(
            new ComplexNumber[][] {
                {new ComplexNumber(1, 2), new ComplexNumber(3, 4)}
            }
        );

        ComplexMatrix result = matrix.conjugateTranspose();

        assertEquals(2, result.getRows());
        assertEquals(1, result.getCols());
        assertEquals(new ComplexNumber(1, -2), result.get(0, 0));
        assertEquals(new ComplexNumber(3, -4), result.get(1, 0));
    }

    @Test
    void testDimensionMismatch() {
        ComplexMatrix a = new ComplexMatrix(2, 2);
        ComplexMatrix b = new ComplexMatrix(3, 3);

        assertThrows(
            IllegalArgumentException.class,
            () -> a.add(b)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> a.multiply(b)
        );
    }

    @Test
    void testCopyIndependence() {
        ComplexMatrix original = createMatrixA();
        ComplexMatrix copy = new ComplexMatrix(original);

        ComplexMatrix changed = copy.withElement(
            0, 0, new ComplexNumber(100, 0)
        );

        assertEquals(new ComplexNumber(1, 0),
            original.get(0, 0));

        assertEquals(new ComplexNumber(100, 0),
            changed.get(0, 0));
    }

    @Test
    void testRandomMatrix() {
        ComplexMatrix matrix =
            ComplexMatrix.random(3, 3, -10, 10);

        assertEquals(3, matrix.getRows());
        assertEquals(3, matrix.getCols());

        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                ComplexNumber number = matrix.get(i, j);

                assertTrue(number.getReal() >= -10);
                assertTrue(number.getReal() < 10);
                assertTrue(number.getImaginary() >= -10);
                assertTrue(number.getImaginary() < 10);
            }
        }
    }
}
