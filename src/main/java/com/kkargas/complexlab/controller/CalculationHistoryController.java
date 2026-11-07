package com.kkargas.complexlab.controller;

import com.kkargas.complexlab.model.CalculationHistory.Type;
import com.kkargas.complexlab.service.CalculationHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/history")
public class CalculationHistoryController {
    private final CalculationHistoryService history;

    public CalculationHistoryController(CalculationHistoryService history) {
        this.history = history;
    }

    @GetMapping
    public String index(@RequestParam(required = false) Type type,
                        @RequestParam(defaultValue = "0") int page, Model model) {
        var records = history.getHistory(type, page);
        model.addAttribute("records", records);
        model.addAttribute("selectedType", type);
        return "history";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        history.delete(id);
        redirectAttributes.addFlashAttribute("message", "Calculation removed from history.");
        return "redirect:/history";
    }
}
