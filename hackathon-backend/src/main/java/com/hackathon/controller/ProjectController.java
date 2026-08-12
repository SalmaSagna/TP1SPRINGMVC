package com.hackathon.controller;

import com.hackathon.dto.ProjectRequest;
import com.hackathon.entities.Project;
import com.hackathon.entities.User;
import com.hackathon.service.ProjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Project submitProject(@RequestBody ProjectRequest request, @AuthenticationPrincipal User user) {
        return projectService.submitProject(request, user);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable("id") Integer id, @RequestBody ProjectRequest request) {
        return projectService.updateProject(id, request);
    }
}