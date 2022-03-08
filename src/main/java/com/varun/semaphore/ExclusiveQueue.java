package com.varun.semaphore;

import java.util.concurrent.Semaphore;

/*
 * ExclusiveQueue guarantees that perform() is conducted by one pair of leader-follower at a
 * time.
 * */
public class ExclusiveQueue {

  private final Semaphore mutex;
  private final Semaphore leaderQueue;
  private final Semaphore followerQueue;
  private final Semaphore performance;
  private int leaders = 0;
  private int followers = 0;

  public ExclusiveQueue() {
    this.leaders = 0;
    this.followers = 0;
    this.mutex = new Semaphore(1);
    this.leaderQueue = new Semaphore(0);
    this.followerQueue = new Semaphore(0);
    this.performance = new Semaphore(0);
  }

  public void enterLeader() throws InterruptedException {
    mutex.acquire();
    if (followers > 0) {
      followers--;
      followerQueue.release();
    } else {
      leaders++;
      mutex.release();
      leaderQueue.acquire();
    }

    perform();
    performance.acquire();
    mutex.release();
  }

  public void enterFollower() throws InterruptedException {
    mutex.acquire();
    if (leaders > 0) {
      leaders--;
      leaderQueue.release();
    } else {
      followers++;
      mutex.release();
      followerQueue.acquire();
    }

    perform();
    performance.release();
  }

  private void perform() {
    System.out.println("Performing");
  }
}
