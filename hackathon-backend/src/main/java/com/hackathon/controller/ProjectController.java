package com.hackathon.controller;

import com.hackathon.service.ProjectService;
import com.hackathon.dto.ProjectRequest;
import com.hackathon.entities.Project;
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
    public Project submitProject(@RequestBody ProjectRequest request, @RequestParam Integer userId) {
        return projectService.submitProject(request, userId);
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