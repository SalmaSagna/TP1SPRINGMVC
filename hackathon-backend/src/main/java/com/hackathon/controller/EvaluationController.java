package com.hackathon.controller;

import com.hackathon.service.EvaluationService;
import com.hackathon.dto.EvaluationRequest;
import com.hackathon.entities.Evaluation;
import com.hackathon.entities.Project;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jury")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("/evaluations")
    public Evaluation evaluate(@RequestBody EvaluationRequest request) {
        return evaluationService.evaluate(request);
    }

    @GetMapping("/projects")
    public List<Project> getProjectsToEvaluate() {
        return evaluationService.getProjectsToEvaluate();
    }
}