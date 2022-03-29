package com.varun.classicalproblems.readerwriter;

public class Reader implements Runnable {

  private final Storage storage;

  public Reader(Storage storage) {
    this.storage = storage;
  }

  @Override
  public void run() {
    while (true) {
      try {
        this.storage.readData();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
