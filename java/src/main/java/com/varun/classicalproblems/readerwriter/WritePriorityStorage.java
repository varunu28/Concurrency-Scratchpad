package com.varun.classicalproblems.readerwriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/*
 * This variation of reader-writer gives more priority to writers. Readers are allowed only when all writers have
 * finished processing.
 * */
public class WritePriorityStorage {

  private final List<String> data;

  private final LightSwitch readSwitch;
  private final LightSwitch writeSwitch;
  private final Semaphore noReaders;
  private final Semaphore noWriters;

  public WritePriorityStorage() {
    this.data = new ArrayList<>();
    this.readSwitch = new LightSwitch();
    this.writeSwitch = new LightSwitch();
    this.noReaders = new Semaphore(1);
    this.noWriters = new Semaphore(1);
  }

  public void writeToData(String entry) throws InterruptedException {
    this.writeSwitch.lock(this.noReaders);
    this.noWriters.acquire();
    System.out.println("Writing entry: " + entry);
    this.noWriters.release();
    this.writeSwitch.unlock(this.noReaders);
  }

  public void readData() throws InterruptedException {
    this.noReaders.acquire();
    this.readSwitch.lock(noWriters);
    this.noReaders.release();
    System.out.println("Reading data of size: " + this.data.size());
    this.readSwitch.unlock(noWriters);
  }
}
