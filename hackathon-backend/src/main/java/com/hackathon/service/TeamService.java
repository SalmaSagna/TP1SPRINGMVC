package com.hackathon.service;

import com.hackathon.repository.TeamMemberRepository;
import com.hackathon.repository.TeamRepository;
import com.hackathon.repository.UserRepository;
import com.hackathon.entities.Team;
import com.hackathon.entities.TeamMember;
import com.hackathon.entities.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository,
                       UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.userRepository = userRepository;
    }

    public Team createTeam(String name, Integer creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

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

    public void joinTeam(Integer teamId, Integer userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

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