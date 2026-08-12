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
        if (teamMemberRepository.findByUser(creator).isPresent()) {
            throw new IllegalStateException("Vous êtes déjà membre d'une équipe. Quittez-la avant d'en créer une nouvelle.");
        }

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
        if (teamMemberRepository.findByUser(user).isPresent()) {
            throw new IllegalStateException("Vous êtes déjà membre d'une équipe. Quittez-la avant d'en rejoindre une nouvelle.");
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable"));

        TeamMember member = new TeamMember();
        member.setUser(user);
        member.setTeam(team);
        teamMemberRepository.save(member);
    }

    public void leaveTeam(Integer teamId, User user) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable"));

        TeamMember membership = teamMemberRepository.findByUserAndTeam(user, team)
                .orElseThrow(() -> new IllegalStateException("Vous n'êtes pas membre de cette équipe"));

        teamMemberRepository.delete(membership);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getMyTeam(User user) {
        return teamMemberRepository.findByUser(user)
                .map(TeamMember::getTeam)
                .orElse(null);
    }
}