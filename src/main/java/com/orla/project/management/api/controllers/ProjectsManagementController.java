package com.orla.project.management.api.controllers;

import com.orla.project.management.api.dtos.ProjectDto;
import com.orla.project.management.api.models.Project;
import com.orla.project.management.api.services.ProjectsManagementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectsManagementController {
  private final ProjectsManagementService projectService;
  private static final Logger logger = LoggerFactory.getLogger(ProjectsManagementController.class);

  public ProjectsManagementController(ProjectsManagementService projectService) {
    this.projectService = projectService;
  }

  @PostMapping
  public ResponseEntity<Project> createProject(
      @RequestHeader("Authorization") @NotBlank String bearerToken,
      @RequestBody @Valid ProjectDto project
  ) {
    Project savedProject = projectService.createProject(project);
    logger.info("Projeto com nome {} foi salvo com sucesso!", project.nome());
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
  }
}
