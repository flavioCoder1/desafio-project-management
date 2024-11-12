package com.orla.project.management.api.services;

import com.orla.project.management.api.dtos.ProjectDto;
import com.orla.project.management.api.exceptions.ProjectAlreadyExists;
import com.orla.project.management.api.mappers.ProjectsManagementMapper;
import com.orla.project.management.api.models.Project;
import com.orla.project.management.api.repositories.ProjectsManagementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsManagementService {
  private final ProjectsManagementRepository projectsManagementRepository;
  private final ProjectsManagementMapper projectsManagementMapper;
  private static final Logger logger = LoggerFactory.getLogger(ProjectsManagementService.class);

  public ProjectsManagementService(ProjectsManagementRepository projectsManagementRepository, ProjectsManagementMapper projectsManagementMapper) {
    this.projectsManagementRepository = projectsManagementRepository;
    this.projectsManagementMapper = projectsManagementMapper;
  }

  public Project createProject(ProjectDto projectDto) {
    Project project = projectsManagementMapper.projectDtoToEntity(projectDto);
    if (projectsManagementRepository.findByNome(projectDto.nome()).isPresent()) {
      throw new ProjectAlreadyExists(project.getNome());
    }
    logger.info("Projeto {} sendo enviado ao banco...", project.getNome());
    return projectsManagementRepository.save(project);
  }

  public List<Project> getAllProjects() {
    logger.info("Procurando projetos no banco...");
    return projectsManagementRepository.findAll();
  }
}
