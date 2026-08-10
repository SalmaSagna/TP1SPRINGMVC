package com.hackathon.dto;
import lombok.Data;

@Data
public class ProjectRequest {
    private String title;
    private String description;
    private String githubLink;
}