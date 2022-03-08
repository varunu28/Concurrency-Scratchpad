package com.varun.semaphore;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class BarrierDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecuteAfterBarrier executeAfterBarrier = new ExecuteAfterBarrier(5);
    Callable<Object> task = () -> {
      executeAfterBarrier.execute();
      return null;
    };

    TestingUtil testingUtil = new TestingUtil(Collections.nCopies(5, task), 1);
    testingUtil.test();
  }
}

/*
 * We want to ensure that critical code is executed only after n threads join. Also, we want only n threads
 * to execute the code and remaining threads to be blocked.
 *
 * This is an example of reusable barrier.
 * */
class ExecuteAfterBarrier {

  private final int numberOfThreads;
  private final Semaphore mutex;
  private final Semaphore turnstileOne;
  private final Semaphore turnstileTwo;
  private int count;

  public ExecuteAfterBarrier(int numberOfThreads) {
    this.numberOfThreads = numberOfThreads;
    this.count = 0;
    this.mutex = new Semaphore(1);
    this.turnstileOne = new Semaphore(0);
    this.turnstileTwo = new Semaphore(1);
  }

  public void execute() throws InterruptedException {
    // Increment count of number of threads. If limit is reached then release turnstileOne & acquire turnstileTwo
    this.mutex.acquire();
    this.count++;
    if (this.count == this.numberOfThreads) {
      this.turnstileTwo.acquire();
      this.turnstileOne.release();
    }
    this.mutex.release();

    this.turnstileOne.acquire();
    this.turnstileOne.release();

    // Critical section
    System.out.println("Executing");

    this.mutex.acquire();
    this.count--;
    if (this.count == 0) {
      this.turnstileOne.acquire();
      this.turnstileTwo.release();
    }
    this.mutex.release();

    this.turnstileTwo.acquire();
    this.turnstileTwo.release();
  }
}