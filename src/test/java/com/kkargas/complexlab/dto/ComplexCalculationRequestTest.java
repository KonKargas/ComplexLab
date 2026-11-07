
package com.kkargas.complexlab.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import com.kkargas.complexlab.enums.CalculationOperation;

class ComplexCalculationRequestTest {

    @Test
    void testMissingFields() {
        try (ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            ComplexCalculationRequest request =
                new ComplexCalculationRequest();

            Set<ConstraintViolation<ComplexCalculationRequest>>
                violations = validator.validate(request);

            // 2 missing components per number + operation
            assertEquals(5, violations.size());
        }
    }

    @Test
    void testValidRequest() {
        try (ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            ComplexCalculationRequest request =
                new ComplexCalculationRequest();

            request.setFirst(new ComplexNumberDTO(3.0, 2.0));
            request.setSecond(new ComplexNumberDTO(1.0, -4.0));
            request.setOperation(CalculationOperation.ADD);

            assertTrue(validator.validate(request).isEmpty());
        }
    }
}
