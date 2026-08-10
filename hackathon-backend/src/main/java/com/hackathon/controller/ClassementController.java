package com.hackathon.controller;

import com.hackathon.service.ClassementService;
import com.hackathon.dto.ClassementRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class ClassementController {

    private final ClassementService classementService;

    public ClassementController(ClassementService classementService) {
        this.classementService = classementService;
    }

    @GetMapping
    public List<ClassementRequest> getClassements() {
        return classementService.getClassement();
    }
}