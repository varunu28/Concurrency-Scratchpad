package com.varun.semaphore;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class SignallingPuzzleDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    SignallingPuzzle signallingPuzzle = new SignallingPuzzle();
    Callable<Object> task1 =
        () -> {
          try {
            signallingPuzzle.printA();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return null;
        };
    Callable<Object> task2 =
        () -> {
          try {
            signallingPuzzle.printB();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return null;
        };

    TestingUtil testingUtil = new TestingUtil(Arrays.asList(task1, task2), 5);
    testingUtil.test();
  }
}

/*
 * Goal is to ensure that following execution order is mandated:
 *   - a1 happens before a2
 *   - b1 happens before b2
 *   - a1 happens before b2
 *   - b1 happens before a2
 *
 * Any order that fulfils above execution flow is valid.
 * */
class SignallingPuzzle {
  private final Semaphore a1Done;
  private final Semaphore b1Done;

  public SignallingPuzzle() {
    this.a1Done = new Semaphore(0);
    this.b1Done = new Semaphore(0);
  }

  public void printA() throws InterruptedException {
    System.out.println("a1");
    this.a1Done.release();
    this.b1Done.acquire();
    System.out.println("a2");
  }

  public void printB() throws InterruptedException {
    System.out.println("b1");
    this.b1Done.release();
    this.a1Done.acquire();
    System.out.println("b2");
  }
}
