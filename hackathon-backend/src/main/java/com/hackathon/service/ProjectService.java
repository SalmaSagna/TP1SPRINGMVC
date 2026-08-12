package com.hackathon.service;

import com.hackathon.dto.ProjectRequest;
import com.hackathon.entities.Project;
import com.hackathon.entities.Team;
import com.hackathon.entities.TeamMember;
import com.hackathon.entities.User;
import com.hackathon.repository.ProjectRepository;
import com.hackathon.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;

    public ProjectService(ProjectRepository projectRepository, TeamMemberRepository teamMemberRepository) {
        this.projectRepository = projectRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public Project submitProject(ProjectRequest request, User user) {
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