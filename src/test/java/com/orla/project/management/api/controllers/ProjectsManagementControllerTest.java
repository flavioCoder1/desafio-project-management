package com.orla.project.management.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orla.project.management.api.dtos.ProjectDto;
import com.orla.project.management.api.exceptions.ProjectsManagementExceptionHandler;
import com.orla.project.management.api.models.Employee;
import com.orla.project.management.api.models.Project;
import com.orla.project.management.api.services.ProjectsManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectsManagementControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ProjectsManagementService projectsManagementService;

  @InjectMocks
  private ProjectsManagementController projectsManagementController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders
        .standaloneSetup(projectsManagementController)
        .setControllerAdvice(new ProjectsManagementExceptionHandler())
        .build();
  }

  @Test
  public void shouldReturnProjectSavedWhenCallingCreateNewProject() throws Exception {
    final ObjectMapper objectMapper = new ObjectMapper();

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
    ProjectDto projectDto = new ProjectDto("concrete", employees);

    Project project = new Project(
        UUID.fromString("1fc5e964-e5b4-468a-b3da-6865509a72fc"),
        "concrete",
        "2024-11-12T02:17:46.443",
        employees
    );

    when(projectsManagementService.createProject(any(ProjectDto.class))).thenReturn(project);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/projetos")
        .header("Authorization", "whatever")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(projectDto));

    this.mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(project)));

    verify(projectsManagementService, times(1)).createProject(any(ProjectDto.class));
  }

  @Test
  public void shouldReturnProjectsWhenCallingGetAllProjects() throws Exception {
    final ObjectMapper objectMapper = new ObjectMapper();

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

    when(projectsManagementService.getAllProjects()).thenReturn(projects);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/projetos")
        .header("Authorization", "whatever")
        .accept(MediaType.APPLICATION_JSON);

    this.mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(projects)));

    verify(projectsManagementService, times(1)).getAllProjects();
  }
}
