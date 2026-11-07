package com.kkargas.complexlab.controller;

import com.kkargas.complexlab.dto.MatrixCalculationRequest;
import com.kkargas.complexlab.enums.MatrixOperation;
import com.kkargas.complexlab.model.ComplexMatrix;
import com.kkargas.complexlab.model.ComplexNumber;
import com.kkargas.complexlab.service.ComplexMatrixService;
import com.kkargas.complexlab.service.MatrixInputParser;
import com.kkargas.complexlab.service.CalculationHistoryService;
import com.kkargas.complexlab.model.CalculationHistory.Type;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matrix")
public class MatrixCalculatorController {
    private final ComplexMatrixService matrixService;
    private final MatrixInputParser parser;
    private final CalculationHistoryService history;

    public MatrixCalculatorController(ComplexMatrixService matrixService, MatrixInputParser parser, CalculationHistoryService history) {
        this.matrixService = matrixService;
        this.parser = parser;
        this.history = history;
    }

    @ModelAttribute("operations")
    public MatrixOperation[] operations() {
        return MatrixOperation.values();
    }

    @GetMapping
    public String showCalculator(Model model) {
        model.addAttribute("matrixRequest", new MatrixCalculationRequest());
        return "matrix-calculator";
    }

    @PostMapping
    public String calculate(@Valid @ModelAttribute("matrixRequest") MatrixCalculationRequest request,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "matrix-calculator";
        try {
            MatrixOperation operation = request.getOperation();
            ComplexMatrix a = parser.parse(request.getMatrixA());
            ComplexMatrix b = operation.isNeedsB() ? parser.parse(request.getMatrixB()) : null;
            model.addAttribute("matrixA", a);
            if (b != null) model.addAttribute("matrixB", b);

            switch (operation) {
                case ADD -> model.addAttribute("matrixResult", matrixService.add(a, b));
                case SUBTRACT -> model.addAttribute("matrixResult", matrixService.subtract(a, b));
                case MULTIPLY -> model.addAttribute("matrixResult", matrixService.multiply(a, b));
                case SCALAR_MULTIPLY -> model.addAttribute("matrixResult", matrixService.scalarMultiply(a,
                    new ComplexNumber(required(request.getScalarReal(), "Scalar real"),
                                      required(request.getScalarImaginary(), "Scalar imaginary"))));
                case TRANSPOSE -> model.addAttribute("matrixResult", matrixService.transpose(a));
                case CONJUGATE_TRANSPOSE -> model.addAttribute("matrixResult", matrixService.conjugateTranspose(a));
                case DETERMINANT -> model.addAttribute("scalarResult", matrixService.determinant(a));
                case TRACE -> model.addAttribute("scalarResult", matrixService.trace(a));
                case INVERSE -> model.addAttribute("matrixResult", matrixService.inverse(a));
                case POWER -> {
                    Integer exponent = request.getExponent();
                    if (exponent == null || exponent < 0 || exponent > 20)
                        throw new IllegalArgumentException("Exponent must be an integer between 0 and 20.");
                    model.addAttribute("matrixResult", matrixService.power(a, exponent));
                }
                case IDENTITY -> model.addAttribute("matrixResult", matrixService.identity(a.getRows()));
                case RANDOM -> model.addAttribute("matrixResult", matrixService.random(a.getRows(), a.getCols(),
                    required(request.getRandomMin(), "Random minimum"),
                    required(request.getRandomMax(), "Random maximum")));
            }
            model.addAttribute("operationLabel", operation.getLabel());

            ComplexMatrix matrixResult = (ComplexMatrix) model.getAttribute("matrixResult");
            ComplexNumber scalarResult = (ComplexNumber) model.getAttribute("scalarResult");
            String resultText = matrixResult != null ? matrixResult.toString() : scalarResult.toString();
            String options = switch (operation) {
                case POWER -> "exponent=" + request.getExponent();
                case SCALAR_MULTIPLY -> "scalar=" + request.getScalarReal() + ":" + request.getScalarImaginary();
                case RANDOM -> "range=" + request.getRandomMin() + ":" + request.getRandomMax();
                default -> null;
            };
            String secondInput = b != null ? request.getMatrixB() : options;
            history.save(Type.MATRIX, operation.name(), request.getMatrixA(), secondInput, resultText);
        } catch (IllegalArgumentException | ArithmeticException ex) {
            model.addAttribute("matrixError", ex.getMessage());
        }
        return "matrix-calculator";
    }

    private double required(Double value, String name) {
        if (value == null || !Double.isFinite(value))
            throw new IllegalArgumentException(name + " must be a finite number.");
        return value;
    }
}
