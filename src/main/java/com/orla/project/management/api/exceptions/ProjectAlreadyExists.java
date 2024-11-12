package com.orla.project.management.api.exceptions;

public class ProjectAlreadyExists extends RuntimeException {
  private static final String MESSAGE = "Projeto %s já existe";
  public ProjectAlreadyExists(String name) {
    super(String.format(MESSAGE, name));
  }
}
