package com.orla.project.management.api.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "funcionarios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotBlank
  @Column(nullable = false)
  private String nome;

  @NotBlank
  @Column(nullable = false)
  private String cpf;

  @NotBlank
  @Email
  @Column(nullable = false)
  private String email;

  @NotNull
  @Positive
  @Column(nullable = false)
  private BigDecimal salario;

  @JsonIgnore
  @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
  private Set<Project> projects;

  public Employee(){}

  public Employee(
      UUID id,
      String nome,
      String cpf,
      String email,
      BigDecimal salario,
      Set<Project> projects) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.email = email;
    this.salario = salario;
    this.projects = projects;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public BigDecimal getSalario() {
    return salario;
  }

  public void setSalario(BigDecimal salario) {
    this.salario = salario;
  }

  public Set<Project> getProjects() {
    return projects;
  }

  public void setProjects(Set<Project> projects) {
    this.projects = projects;
  }

  @JsonProperty("projetos")
  public List<String> getProjectNames() {
    return projects == null ? null : projects.stream()
        .map(Project::getNome)
        .collect(Collectors.toList());
  }
}

