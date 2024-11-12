package com.orla.project.management.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orla.project.management.api.models.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public record ProjectDto(
    @NotBlank(message = "Um projeto tem que ter um nome")
    String nome,
    @JsonProperty("funcionarios")
    @NotNull
    @Size(min = 1, message = "Um projeto deve ter pelo menos 1 funcionário")
    Set<Employee> employees
) {
  public ProjectDto {
    employees = null == employees ? new HashSet<>() : new HashSet<>(employees);
  }
}
