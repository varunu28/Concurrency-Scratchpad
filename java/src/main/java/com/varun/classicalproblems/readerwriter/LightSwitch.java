package com.varun.classicalproblems.readerwriter;

import java.util.concurrent.Semaphore;

public class LightSwitch {
  private final Semaphore mutex;
  private int counter;

  public LightSwitch() {
    this.counter = 0;
    this.mutex = new Semaphore(1);
  }

  public void lock(Semaphore semaphore) throws InterruptedException {
    this.mutex.acquire();
    this.counter++;
    if (this.counter == 1) {
      semaphore.acquire();
    }
    this.mutex.release();
  }

  public void unlock(Semaphore semaphore) throws InterruptedException {
    this.mutex.acquire();
    this.counter--;
    if (this.counter == 0) {
      semaphore.release();
    }
    this.mutex.release();
  }
}
