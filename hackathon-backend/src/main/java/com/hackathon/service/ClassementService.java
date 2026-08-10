package com.hackathon.service;

import com.hackathon.repository.EvaluationRepository;
import com.hackathon.repository.ProjectRepository;
import com.hackathon.dto.ClassementRequest;
import com.hackathon.entities.Evaluation;
import com.hackathon.entities.Project;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClassementService {

    private final ProjectRepository projectRepository;
    private final EvaluationRepository evaluationRepository;

    public ClassementService(ProjectRepository projectRepository, EvaluationRepository evaluationRepository) {
        this.projectRepository = projectRepository;
        this.evaluationRepository = evaluationRepository;
    }

    public List<ClassementRequest> getClassement() {
        List<Project> projects = projectRepository.findAll();

        //calcul du score moyen de chaque projet
        List<ClassementRequest> classementSansRang = projects.stream()
                .map(project -> {
                    List<Evaluation> evaluations = evaluationRepository.findByProject(project);
                    double moyenne = evaluations.stream()
                            .mapToInt(Evaluation::getScore)
                            .average()
                            .orElse(0.0);
                    // rang = 0 provisoire, on le remplit juste après
                    return new ClassementRequest(project.getTeam().getName(), project.getTitle(), moyenne, 0);
                })
                .sorted(Comparator.comparingDouble(ClassementRequest::getFinalScore).reversed())
                .collect(Collectors.toList());

        //on attribue le rang selon la position
        return IntStream.range(0, classementSansRang.size())
                .mapToObj(i -> {
                    ClassementRequest entry = classementSansRang.get(i);
                    entry.setRang(i + 1);
                    return entry;
                })
                .collect(Collectors.toList());
    }
}