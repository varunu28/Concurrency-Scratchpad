package com.varun.classicalproblems.readerwriter;

import java.util.UUID;

public class Writer implements Runnable {

  private final Storage storage;

  public Writer(Storage storage) {
    this.storage = storage;
  }

  @Override
  public void run() {
    while (true) {
      try {
        this.storage.writeToData(UUID.randomUUID().toString());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
