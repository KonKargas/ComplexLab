package com.kkargas.complexlab.controller;

import com.kkargas.complexlab.dto.ComplexCalculationRequest;
import com.kkargas.complexlab.dto.ComplexCalculationResponse;
import com.kkargas.complexlab.enums.CalculationOperation;
import com.kkargas.complexlab.model.ComplexNumber;
import com.kkargas.complexlab.service.ComplexNumberService;
import com.kkargas.complexlab.service.CalculationHistoryService;
import com.kkargas.complexlab.model.CalculationHistory.Type;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {

    private final ComplexNumberService numberService;
    private final CalculationHistoryService history;

    public CalculatorController(ComplexNumberService numberService, CalculationHistoryService history) {
        this.numberService = numberService;
        this.history = history;
    }

    @ModelAttribute("operations")
    public CalculationOperation[] operations() {
        return CalculationOperation.values();
    }

    @GetMapping
    public String showCalculator(Model model) {

        ComplexCalculationRequest request =
            new ComplexCalculationRequest();

        request.setOperation(CalculationOperation.ADD);

        model.addAttribute("calculationRequest", request);

        return "calculator";
    }

    @PostMapping
    public String calculate(
            @Valid @ModelAttribute("calculationRequest")
                ComplexCalculationRequest request,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "calculator";
        }

        try {
            ComplexNumber first = request.getFirst().toModel();
            ComplexNumber second = request.getSecond().toModel();

            ComplexNumber result = switch (request.getOperation()) {
                case ADD -> numberService.add(first, second);
                case SUBTRACT -> numberService.subtract(first, second);
                case MULTIPLY -> numberService.multiply(first, second);
                case DIVIDE -> numberService.divide(first, second);
            };

            ComplexCalculationResponse response =
                ComplexCalculationResponse.of(
                    first, second, request.getOperation(), result
                );

            model.addAttribute("calculationResult", response);

            history.save(Type.COMPLEX_NUMBER, request.getOperation().name(),
                    first.toString(), second.toString(), result.toString());

        } catch (ArithmeticException | IllegalArgumentException ex) {
            model.addAttribute("calculationError", ex.getMessage());
        }

        return "calculator";
    }
}
