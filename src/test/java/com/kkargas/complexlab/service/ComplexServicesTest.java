
package com.kkargas.complexlab.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kkargas.complexlab.model.ComplexMatrix;
import com.kkargas.complexlab.model.ComplexNumber;

@SpringBootTest
class ComplexServicesTest {

    @Autowired
    private ComplexNumberService numberService;

    @Autowired
    private ComplexMatrixService matrixService;

    @Test
    void testNumberService() {
        ComplexNumber a = numberService.create(3, 2);
        ComplexNumber b = numberService.create(1, -4);

        ComplexNumber result = numberService.add(a, b);

        assertEquals(new ComplexNumber(4, -2), result);
    }

    @Test
    void testMatrixService() {
        ComplexMatrix a = matrixService.identity(2);
        ComplexMatrix b = matrixService.identity(2);

        ComplexMatrix result = matrixService.add(a, b);

        assertEquals(
            new ComplexNumber(2, 0),
            result.get(0, 0)
        );

        assertEquals(
            new ComplexNumber(0, 0),
            result.get(0, 1)
        );
    }

    @Test
    void testPolarConversion() {
        ComplexNumber result = numberService.fromPolar(
            5, Math.atan2(4, 3)
        );

        assertEquals(3, result.getReal(), 1e-9);
        assertEquals(4, result.getImaginary(), 1e-9);
    }
}
