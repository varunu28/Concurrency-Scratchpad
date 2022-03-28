package com.varun.semaphore;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class MutexDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CounterIncrementer counterIncrementer = new CounterIncrementer();

    Callable<Object> incrementTask =
        () -> {
          counterIncrementer.incrementCount();
          return null;
        };

    TestingUtil testingUtil = new TestingUtil(Collections.nCopies(10, incrementTask), 1);
    testingUtil.test();
  }
}

/*
 * Aim is to increment the count variable atomically when multiple threads are trying to increment
 * the count at the same time.
 * */
class CounterIncrementer {

  private final Semaphore semaphore;
  private int count;

  public CounterIncrementer() {
    this.count = 0;
    this.semaphore = new Semaphore(1);
  }

  public void incrementCount() throws InterruptedException {
    this.semaphore.acquire();
    // Critical section starts
    this.count++;
    // Critical section ends
    System.out.println("Count incremented to: " + this.count);
    this.semaphore.release();
  }
}
