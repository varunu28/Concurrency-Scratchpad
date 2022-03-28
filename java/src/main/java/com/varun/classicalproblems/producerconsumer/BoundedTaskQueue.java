package com.varun.classicalproblems.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BoundedTaskQueue {
  private static final int QUEUE_CAPACITY = 10;
  private static BoundedTaskQueue boundedTaskQueue;

  private final Queue<Task> queue;

  private final Semaphore mutex;
  private final Semaphore items;
  private final Semaphore capacity;

  private BoundedTaskQueue() {
    this.queue = new LinkedList<>();
    this.mutex = new Semaphore(1);
    this.items = new Semaphore(0);
    this.capacity = new Semaphore(QUEUE_CAPACITY);
  }

  public void addTask(Task task) throws InterruptedException {
    this.capacity.acquire();
    this.mutex.acquire();
    this.queue.add(task);
    this.mutex.release();
    this.items.release();
  }

  public Task getTask() throws InterruptedException {
    this.items.acquire();
    this.mutex.acquire();
    Task task = this.queue.remove();
    this.mutex.release();
    this.capacity.release();
    return task;
  }

  public static synchronized BoundedTaskQueue getInstance() {
    if (boundedTaskQueue == null) {
      boundedTaskQueue = new BoundedTaskQueue();
    }
    return boundedTaskQueue;
  }
}
