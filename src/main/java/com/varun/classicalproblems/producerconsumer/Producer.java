package com.varun.classicalproblems.producerconsumer;

import java.util.UUID;

public class Producer implements Runnable {

  private final BoundedTaskQueue taskQueue;

  public Producer(BoundedTaskQueue taskQueue) {
    this.taskQueue = taskQueue;
  }

  @Override
  public void run() {
    while (true) {
      try {
        taskQueue.addTask(new Task(UUID.randomUUID().toString()));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
