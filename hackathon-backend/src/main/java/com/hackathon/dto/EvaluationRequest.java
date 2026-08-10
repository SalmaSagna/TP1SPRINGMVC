package com.hackathon.dto;
import lombok.Data;

@Data
public class EvaluationRequest {
    private Integer projectId;
    private Integer juryId;
    private Integer score;
    private String comment;
}