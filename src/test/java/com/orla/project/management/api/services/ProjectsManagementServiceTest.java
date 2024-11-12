package com.orla.project.management.api.services;

import com.orla.project.management.api.dtos.ProjectDto;
import com.orla.project.management.api.exceptions.ProjectAlreadyExists;
import com.orla.project.management.api.mappers.ProjectsManagementMapper;
import com.orla.project.management.api.models.Employee;
import com.orla.project.management.api.models.Project;
import com.orla.project.management.api.repositories.ProjectsManagementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectsManagementServiceTest {

  @Mock
  ProjectsManagementRepository projectsManagementRepository;

  @InjectMocks
  ProjectsManagementService projectsManagementService;

  private ProjectsManagementMapper projectsManagementMapper;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    projectsManagementMapper = spy(new ProjectsManagementMapper());
    projectsManagementService = new ProjectsManagementService(projectsManagementRepository, projectsManagementMapper);
  }

  @Test
  public void shouldSaveAndReturnProjectWhenCallingCreateProject() {
    Set<Employee> employees = Set.of(
        new Employee(
            UUID.fromString("e3aa22cf-d5a9-4e4a-a545-d991979ecff9"),
            "Employee",
            "123.456.789-00",
            "employee@example.com",
            BigDecimal.valueOf(5000.00),
            null
        )
    );
    Project project = new Project(
        UUID.fromString("1fc5e964-e5b4-468a-b3da-6865509a72fc"),
        "concrete",
        "2024-11-12T02:17:46.443",
        employees
    );
    ProjectDto projectDto = new ProjectDto("concrete", employees);
    when(projectsManagementRepository.save(any(Project.class))).thenReturn(project);
    when(projectsManagementRepository.findByNome(anyString())).thenReturn(Optional.empty());
    when(projectsManagementMapper.projectDtoToEntity(projectDto)).thenCallRealMethod();

    Project result = projectsManagementService.createProject(projectDto);

    assertEquals(project, result);

    verify(projectsManagementRepository, times(1)).save(any(Project.class));
    verify(projectsManagementRepository, times(1)).findByNome(anyString());
    verify(projectsManagementMapper, times(1)).projectDtoToEntity(any(ProjectDto.class));
  }

  @Test
  public void shouldThrowProjectAlreadyExistsExceptionWhenCreatingProjectThatAlreadyExists() {
    Set<Employee> employees = Set.of(
        new Employee(
            UUID.fromString("e3aa22cf-d5a9-4e4a-a545-d991979ecff9"),
            "Employee",
            "123.456.789-00",
            "employee@example.com",
            BigDecimal.valueOf(5000.00),
            null
        )
    );
    Project project = new Project(
        UUID.fromString("1fc5e964-e5b4-468a-b3da-6865509a72fc"),
        "concrete",
        "2024-11-12T02:17:46.443",
        employees
    );
    ProjectDto projectDto = new ProjectDto("concrete", employees);

    when(projectsManagementMapper.projectDtoToEntity(projectDto)).thenCallRealMethod();
    when(projectsManagementRepository.findByNome(anyString())).thenReturn(Optional.of(project));

    assertThrows(ProjectAlreadyExists.class, () -> projectsManagementService.createProject(projectDto));

    verify(projectsManagementRepository, times(1)).findByNome(anyString());
    verify(projectsManagementMapper, times(1)).projectDtoToEntity(any(ProjectDto.class));
  }

  @Test
  public void shouldReturnProjectsListWhenCallingListAllProjects() {
    Set<Employee> employees = Set.of(
        new Employee(
            UUID.fromString("e3aa22cf-d5a9-4e4a-a545-d991979ecff9"),
            "Employee",
            "123.456.789-00",
            "employee@example.com",
            BigDecimal.valueOf(5000.00),
            null
        )
    );
    Project project = new Project(
        UUID.fromString("1fc5e964-e5b4-468a-b3da-6865509a72fc"),
        "concrete",
        "2024-11-12T02:17:46.443",
        employees
    );
    Project project2 = new Project(
        UUID.fromString("d805b97e-c46d-4e99-b5b1-5495c291a006"),
        "concrete",
        "2024-11-12T00:10:07.797",
        employees
    );

    List<Project> projects = List.of(project, project2);

    when(projectsManagementRepository.findAll()).thenReturn(projects);

    List<Project> result = projectsManagementService.getAllProjects();

    assertEquals(List.of(project, project2), result);

    verify(projectsManagementRepository, times(1)).findAll();
  }
}
