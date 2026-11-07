package com.kkargas.complexlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MatrixVisualizerController {

    @GetMapping("/matrix-visualizer")
    public String showMatrixVisualizer() {
        return "matrix-visualizer";
    }
}
