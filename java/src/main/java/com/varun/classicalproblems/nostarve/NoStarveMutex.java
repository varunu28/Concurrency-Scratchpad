package com.varun.classicalproblems.nostarve;

import java.util.concurrent.Semaphore;

/*
 * No starve mutex makes use of rooms & turnstiles to ensure that no thread is starved due to competition by other
 * threads. It works as below:
 * 1. Initially a thread acquires the mutex and enters roomOne
 * 2. Then it acquires turnstileOne & enters roomTwo leaving roomOne
 * 3. If roomOne becomes empty, it releases turnstileTwo for start processing roomTwo.
 * 4. Else it just stays in roomTwo & releases turnstileOne for other threads to enter roomTwo.
 * 5. Once roomOne becomes empty, turnstileTwo is released which is acquired by threads in roomTwo
 * 6. Threads in roomTwo start performing critical section and remove themselves from roomTwo
 * 7. If roomTwo becomes empty then turnstileOne is released so threads can start entering roomTwo from roomOne
 * 8. Else processing continues.
 *
 * This flow ensures that a finite number of threads enter the roomTwo to perform processing and are bound to finish
 * execution before more threads come in to compete for critical section.
 * */
public class NoStarveMutex {

  private int roomOne;
  private int roomTwo;
  private final Semaphore mutex;
  private final Semaphore turnstileOne;
  private final Semaphore turnstileTwo;

  public NoStarveMutex() {
    this.roomOne = 0;
    this.roomTwo = 0;
    this.mutex = new Semaphore(1);
    this.turnstileOne = new Semaphore(1);
    this.turnstileTwo = new Semaphore(0);
  }

  public void execute(String task) throws InterruptedException {
    this.mutex.acquire();
    this.roomOne++;
    this.mutex.release();

    this.turnstileOne.acquire();
    this.roomTwo++;
    this.mutex.acquire();
    this.roomOne--;
    if (this.roomOne == 0) {
      this.mutex.release();
      this.turnstileTwo.release();
    } else {
      this.mutex.release();
      this.turnstileOne.release();
    }

    this.turnstileTwo.acquire();
    this.roomTwo--;

    System.out.println("Performing task: " + task);

    if (this.roomTwo == 0) {
      this.turnstileOne.release();
    } else {
      this.turnstileTwo.release();
    }
  }
}
