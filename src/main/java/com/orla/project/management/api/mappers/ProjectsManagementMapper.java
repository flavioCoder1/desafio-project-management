package com.orla.project.management.api.mappers;

import com.orla.project.management.api.dtos.ProjectDto;
import com.orla.project.management.api.models.Project;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class ProjectsManagementMapper {

  public Project projectDtoToEntity(ProjectDto projectDto) {
    return new Project(
        UUID.randomUUID(),
        projectDto.nome(),
        ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")),
        projectDto.employees()
    );
  }
}
