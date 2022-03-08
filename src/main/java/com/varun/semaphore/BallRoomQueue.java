package com.varun.semaphore;

import java.util.concurrent.Semaphore;

/*
 * Leader waits until it finds a follower and vice-versa
 * */
public class BallRoomQueue {
  private final Semaphore leaderQueue;
  private final Semaphore followerQueue;

  public BallRoomQueue() {
    this.leaderQueue = new Semaphore(0);
    this.followerQueue = new Semaphore(0);
  }

  public void enterLeader() throws InterruptedException {
    followerQueue.release();
    leaderQueue.acquire();
    perform();
  }

  public void enterFollower() throws InterruptedException {
    leaderQueue.release();
    followerQueue.acquire();
    perform();
  }

  private void perform() {
    System.out.println("Performing");
  }
}
