package com.varun.classicalproblems.diningphilosopher;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import static com.varun.classicalproblems.diningphilosopher.ForkIndex.leftForkIndex;
import static com.varun.classicalproblems.diningphilosopher.ForkIndex.rightForkIndex;

/*
 * TanenbaumSolution avoids deadlock but is not starvation free and can lead to one of the philosopher to starve while
 *  waiting for other philosophers.
 * */
public class TanenbaumSolution {

  private final List<String> state;
  private final List<Semaphore> semaphores;
  private final Semaphore mutex;

  public TanenbaumSolution() {
    this.state = Collections.nCopies(5, "thinking");
    this.semaphores = Collections.nCopies(5, new Semaphore(0));
    this.mutex = new Semaphore(1);
  }

  public void getFork(int i) throws InterruptedException {
    this.mutex.acquire();
    this.state.set(1, "hungry");
    this.test(i);
    this.mutex.release();
    this.semaphores.get(i).acquire();
  }

  public void putFork(int i) throws InterruptedException {
    this.mutex.acquire();
    this.state.set(i, "thinking");
    this.test(rightForkIndex(i));
    this.test(leftForkIndex(i));
    this.mutex.release();
  }

  private void test(int i) {
    if (this.state.get(i).equals("hungry") &&
        !this.state.get(leftForkIndex(i)).equals("eating") &&
        !this.state.get(rightForkIndex(i)).equals("eating")) {
      this.state.set(i, "eating");
      this.semaphores.get(i).release();
    }
  }
}
