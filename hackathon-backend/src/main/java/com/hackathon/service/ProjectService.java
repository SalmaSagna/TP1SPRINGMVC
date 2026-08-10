package com.hackathon.service;

import com.hackathon.repository.ProjectRepository;
import com.hackathon.repository.TeamMemberRepository;
import com.hackathon.repository.UserRepository;
import com.hackathon.dto.ProjectRequest;
import com.hackathon.entities.Project;
import com.hackathon.entities.Team;
import com.hackathon.entities.TeamMember;
import com.hackathon.entities.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, TeamMemberRepository teamMemberRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.userRepository = userRepository;
    }

    public Project submitProject(ProjectRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        Team team = teamMemberRepository.findByUser(user)
                .map(TeamMember::getTeam)
                .orElseThrow(() -> new IllegalStateException("Vous devez rejoindre une équipe avant de soumettre un projet"));

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setGithubLink(request.getGithubLink());
        project.setTeam(team);

        return projectRepository.save(project);
    }

    public Project updateProject(Integer id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projet introuvable"));

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setGithubLink(request.getGithubLink());

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}