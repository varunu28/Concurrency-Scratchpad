package com.varun.classicalproblems.producerconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Runner {

  public static void main(String[] args) {
    BoundedTaskQueue boundedTaskQueue = BoundedTaskQueue.getInstance();

    ExecutorService producerService = Executors.newFixedThreadPool(2);
    IntStream.range(0, 2).forEach(i -> producerService.submit(new Producer(boundedTaskQueue)));

    ExecutorService consumerService = Executors.newFixedThreadPool(2);
    IntStream.range(0, 2).forEach(i -> consumerService.submit(new Consumer(boundedTaskQueue)));
  }
}
