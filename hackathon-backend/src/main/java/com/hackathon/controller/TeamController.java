package com.hackathon.controller;

import com.hackathon.dto.TeamRequest;
import com.hackathon.entities.Team;
import com.hackathon.entities.User;
import com.hackathon.service.TeamService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Team createTeam(@RequestBody TeamRequest request, @AuthenticationPrincipal User user) {
        return teamService.createTeam(request.getName(), user);
    }

    @PostMapping("/{id}/join")
    public void joinTeam(@PathVariable("id") Integer id, @AuthenticationPrincipal User user) {
        teamService.joinTeam(id, user);
    }

    @DeleteMapping("/{id}/leave")
    public void leaveTeam(@PathVariable("id") Integer id, @AuthenticationPrincipal User user) {
        teamService.leaveTeam(id, user);
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/my-team")
    public Team getMyTeam(@AuthenticationPrincipal User user) {
        return teamService.getMyTeam(user);
    }
}