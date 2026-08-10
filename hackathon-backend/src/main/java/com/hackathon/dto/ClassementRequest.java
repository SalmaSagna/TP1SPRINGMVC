package com.hackathon.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassementRequest {
    private String teamName;
    private String projectTitle;
    private double finalScore;
    private int rang;
}