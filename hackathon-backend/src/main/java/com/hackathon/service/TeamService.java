package com.hackathon.service;

import com.hackathon.entities.Team;
import com.hackathon.entities.TeamMember;
import com.hackathon.entities.User;
import com.hackathon.repository.TeamMemberRepository;
import com.hackathon.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    public TeamService(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public Team createTeam(String name, User creator) {
        Team team = new Team();
        team.setName(name);
        team.setCreatedBy(creator);
        Team saved = teamRepository.save(team);

        TeamMember member = new TeamMember();
        member.setUser(creator);
        member.setTeam(saved);
        teamMemberRepository.save(member);

        return saved;
    }

    public void joinTeam(Integer teamId, User user) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable"));

        if (teamMemberRepository.existsByUserAndTeam(user, team)) {
            throw new IllegalStateException("Vous êtes déjà membre de cette équipe");
        }

        TeamMember member = new TeamMember();
        member.setUser(user);
        member.setTeam(team);
        teamMemberRepository.save(member);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}