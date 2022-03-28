package com.varun.semaphore;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestingUtil {

  private final List<Callable<Object>> tasks;
  private final int testCount;

  public TestingUtil(List<Callable<Object>> tasks, int testCount) {
    this.tasks = Collections.unmodifiableList(tasks);
    this.testCount = testCount;
  }

  public void test() throws InterruptedException, ExecutionException {
    for (int i = 0; i < this.testCount; i++) {
      performComputation();
    }
  }

  private void performComputation() throws InterruptedException, ExecutionException {
    System.out.println("Started");
    int threadPoolSize = tasks.size();
    ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);
    List<Future<Object>> futures = service.invokeAll(tasks);
    for (Future<Object> future : futures) {
      future.get();
    }
    service.shutdown();
    System.out.println("Finished");
  }
}
