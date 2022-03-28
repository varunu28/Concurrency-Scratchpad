package com.varun.classicalproblems.producerconsumer;

public class Consumer implements Runnable {

  private final BoundedTaskQueue taskQueue;

  public Consumer(BoundedTaskQueue taskQueue) {
    this.taskQueue = taskQueue;
  }

  @Override
  public void run() {
    try {
      while (true) {
        System.out.println(Thread.currentThread().getId() + " consuming " + taskQueue.getTask().performTask());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
