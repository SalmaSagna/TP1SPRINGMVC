package com.hackathon.controller;

import com.hackathon.service.TeamService;
import com.hackathon.dto.TeamRequest;
import com.hackathon.entities.Team;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public Team createTeam(@RequestBody TeamRequest request, @RequestParam Integer userId) {
        return teamService.createTeam(request.getName(), userId);
    }

    @PostMapping("/{id}/join")
    public void joinTeam(@PathVariable("id") Integer id, @RequestParam Integer userId) {
        teamService.joinTeam(id, userId);
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }
}