package com.varun.semaphore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiplexDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Cafeteria cafeteria = new Cafeteria();
    List<Occupant> occupants =
        IntStream.range(0, 10).mapToObj(i -> new Occupant()).collect(Collectors.toList());
    List<Callable<Object>> tasks = new ArrayList<>();

    for (Occupant occupant : occupants) {
      Callable<Object> task =
          () -> {
            cafeteria.addOccupant(occupant.occupantId);
            Thread.sleep(100);
            cafeteria.removeOccupant(occupant.occupantId);
            return null;
          };
      tasks.add(task);
    }

    TestingUtil testingUtil = new TestingUtil(tasks, 1);
    testingUtil.test();
  }
}

/*
 * An example of a multiplex is a Cafeteria where we don't allow more occupancy than the prescribed
 * limit. If limit is reached then occupants wait before there is an availability in the cafeteria.
 * */
class Cafeteria {

  private final Semaphore semaphore;
  private final Set<String> occupants;

  public Cafeteria() {
    int limit = 5;
    this.semaphore = new Semaphore(limit);
    this.occupants = new HashSet<>();
  }

  public void addOccupant(String occupantId) throws InterruptedException {
    System.out.println("Current occupancy: " + this.occupants);
    this.semaphore.acquire();
    this.occupants.add(occupantId);
  }

  public void removeOccupant(String occupantId) {
    this.occupants.remove(occupantId);
    this.semaphore.release();
    System.out.println("Remaining occupancy: " + this.occupants);
  }
}

class Occupant {
  String occupantId;

  public Occupant() {
    this.occupantId = String.valueOf(new Date().getTime());
  }
}
