package com.varun.classicalproblems.producerconsumer;

public class Task {
  private final String taskName;

  public Task(String taskName) {
    this.taskName = taskName;
  }

  public String performTask() {
    return "Performing task: " + this.taskName;
  }
}
