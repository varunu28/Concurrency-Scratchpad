package com.varun.classicalproblems.readerwriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Storage {
  private final LightSwitch lightSwitch;
  private final Semaphore roomEmpty;
  private final Semaphore turnstile;
  private final List<String> data;

  public Storage() {
    this.data = new ArrayList<>();
    this.roomEmpty = new Semaphore(1);
    this.turnstile = new Semaphore(1);
    this.lightSwitch = new LightSwitch();
  }

  public void writeToData(String entry) throws InterruptedException {
    this.turnstile.acquire();
    this.roomEmpty.acquire();
    System.out.println("Writing entry: " + entry);
    this.data.add(entry);
    this.turnstile.release();
    this.roomEmpty.release();
  }

  public void readData() throws InterruptedException {
    this.turnstile.acquire();
    this.turnstile.release();
    this.lightSwitch.lock(roomEmpty);
    System.out.println("Reading data of size: " + this.data.size());
    this.lightSwitch.unlock(roomEmpty);
  }
}
