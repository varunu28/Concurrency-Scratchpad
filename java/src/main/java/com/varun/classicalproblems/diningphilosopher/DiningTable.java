package com.varun.classicalproblems.diningphilosopher;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import static com.varun.classicalproblems.diningphilosopher.ForkIndex.leftForkIndex;
import static com.varun.classicalproblems.diningphilosopher.ForkIndex.rightForkIndex;

public class DiningTable {

  private final Semaphore dinersOnTable;
  private final List<Semaphore> forks;

  public DiningTable() {
    this.forks = Collections.nCopies(5, new Semaphore(1));
    this.dinersOnTable = new Semaphore(4);
  }

  public void getForks(int i) throws InterruptedException {
    this.dinersOnTable.acquire();
    this.forks.get(leftForkIndex(i)).acquire();
    this.forks.get(rightForkIndex(i)).acquire();
  }

  public void putForks(int i) {
    this.forks.get(leftForkIndex(i)).release();
    this.forks.get(rightForkIndex(i)).release();
    this.dinersOnTable.release();
  }
}
