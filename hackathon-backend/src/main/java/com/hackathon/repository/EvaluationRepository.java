package com.hackathon.repository;

import com.hackathon.entities.Evaluation;
import com.hackathon.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    List<Evaluation> findByProject(Project project);
}