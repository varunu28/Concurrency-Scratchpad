package com.varun.classicalproblems.readerwriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ReaderWriterRunner {
  public static void main(String[] args) {
    Storage storage = new Storage();

    ExecutorService writerService = Executors.newFixedThreadPool(1);
    IntStream.range(0, 2).forEach(i -> writerService.submit(new Writer(storage)));

    ExecutorService readerService = Executors.newFixedThreadPool(1);
    IntStream.range(0, 2).forEach(i -> readerService.submit(new Reader(storage)));
  }
}
