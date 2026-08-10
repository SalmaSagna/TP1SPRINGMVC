package com.hackathon.service;

import com.hackathon.repository.EvaluationRepository;
import com.hackathon.repository.ProjectRepository;
import com.hackathon.repository.UserRepository;
import com.hackathon.dto.EvaluationRequest;
import com.hackathon.entities.Evaluation;
import com.hackathon.entities.Project;
import com.hackathon.entities.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public EvaluationService(EvaluationRepository evaluationRepository, ProjectRepository projectRepository,
                             UserRepository userRepository) {
        this.evaluationRepository = evaluationRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Evaluation evaluate(EvaluationRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Projet introuvable"));
        User jury = userRepository.findById(request.getJuryId())
                .orElseThrow(() -> new IllegalArgumentException("Jury introuvable"));

        if (request.getScore() == null || request.getScore() < 0 || request.getScore() > 20) {
            throw new IllegalArgumentException("Le score doit être compris entre 0 et 20");
        }

        Evaluation evaluation = new Evaluation();
        evaluation.setProject(project);
        evaluation.setJury(jury);
        evaluation.setScore(request.getScore());
        evaluation.setComment(request.getComment());

        return evaluationRepository.save(evaluation);
    }

    public List<Project> getProjectsToEvaluate() {
        return projectRepository.findAll();
    }
}