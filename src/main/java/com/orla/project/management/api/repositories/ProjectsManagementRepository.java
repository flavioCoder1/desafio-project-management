package com.orla.project.management.api.repositories;

import com.orla.project.management.api.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectsManagementRepository extends JpaRepository<Project, UUID> {
  Optional<Project> findByNome(String nome);
}
