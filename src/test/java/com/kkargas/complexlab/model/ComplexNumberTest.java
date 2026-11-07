
package com.kkargas.complexlab.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {

    private static final double EPSILON = 1e-9;

    @Test
    void testAddition() {
        ComplexNumber a = new ComplexNumber(3, 2);
        ComplexNumber b = new ComplexNumber(1, -4);

        ComplexNumber result = a.add(b);

        assertEquals(4, result.getReal(), EPSILON);
        assertEquals(-2, result.getImaginary(), EPSILON);
    }

    @Test
    void testSubtraction() {
        ComplexNumber a = new ComplexNumber(3, 2);
        ComplexNumber b = new ComplexNumber(1, -4);

        ComplexNumber result = a.subtract(b);

        assertEquals(2, result.getReal(), EPSILON);
        assertEquals(6, result.getImaginary(), EPSILON);
    }

    @Test
    void testMultiplication() {
        ComplexNumber a = new ComplexNumber(3, 2);
        ComplexNumber b = new ComplexNumber(1, -4);

        ComplexNumber result = a.multiply(b);

        assertEquals(11, result.getReal(), EPSILON);
        assertEquals(-10, result.getImaginary(), EPSILON);
    }

    @Test
    void testDivision() {
        ComplexNumber a = new ComplexNumber(4, 2);
        ComplexNumber b = new ComplexNumber(1, 1);

        ComplexNumber result = a.divide(b);

        assertEquals(3, result.getReal(), EPSILON);
        assertEquals(-1, result.getImaginary(), EPSILON);
    }

    @Test
    void testDivisionByZero() {
        ComplexNumber a = new ComplexNumber(3, 2);
        ComplexNumber zero = new ComplexNumber(0, 0);

        assertThrows(
            ArithmeticException.class,
            () -> a.divide(zero)
        );
    }

    @Test
    void testConjugate() {
        ComplexNumber number = new ComplexNumber(3, 4);

        assertEquals(
            new ComplexNumber(3, -4),
            number.conjugate()
        );
    }

    @Test
    void testMagnitude() {
        ComplexNumber number = new ComplexNumber(3, 4);

        assertEquals(5, number.magnitude(), EPSILON);
    }

    @Test
    void testEquality() {
        ComplexNumber a = new ComplexNumber(3, 2);
        ComplexNumber b = new ComplexNumber(3, 2);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
