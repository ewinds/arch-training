import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PerformanceTest {
  @Test
  public void testInvokeAll() {
    ExecutorService executor = Executors.newWorkStealingPool();

    List<Callable<String>> callables =
        Arrays.asList(
            () -> {
              System.out.println(System.currentTimeMillis());
              return "task1";
            },
            () -> {
              System.out.println(System.currentTimeMillis());
              TimeUnit.SECONDS.sleep(5);
              System.out.println(System.currentTimeMillis());
              return "task2";
            },
            () -> {
              System.out.println(System.currentTimeMillis());
              return "task3";
            },
            () -> {
              System.out.println(System.currentTimeMillis());
              return "task4";
            },
            () -> {
              System.out.println(System.currentTimeMillis());
              return "task5";
            });

    try {
      executor.invokeAll(callables).stream()
          .map(
              future -> {
                try {
                  return future.get();
                } catch (Exception e) {
                  throw new IllegalStateException(e);
                }
              })
          .forEach(System.out::println);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      System.out.println("attempt to shutdown executor");
      executor.shutdown();
      executor.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.err.println("tasks interrupted");
    } finally {
      if (!executor.isTerminated()) {
        System.err.println("cancel non-finished tasks");
      }
      executor.shutdownNow();
      System.out.println("shutdown finished");
    }
  }

  @Test
  public void test100Call() {
    ExecutorService executor = Executors.newFixedThreadPool(10);

    Callable<List<Long>> callable =
        () -> {
          List<Long> responseTimes = new ArrayList<>();
          OkHttpClient httpClient = new OkHttpClient();
          Request request = new Request.Builder().url("https://www.baidu.com").get().build();

          for (int i = 0; i < 10; i++) {
            Call call = httpClient.newCall(request);
            Response resp = null;
            try {
              System.out.println(System.currentTimeMillis());
              resp = call.execute();
              responseTimes.add(resp.receivedResponseAtMillis() - resp.sentRequestAtMillis());
            } catch (IOException e) {
              responseTimes.add(0L);
            } finally {
              if (resp != null) {
                resp.close();
              }
            }
          }

          return responseTimes;
        };
    List<Callable<List<Long>>> callables = Collections.nCopies(10, callable);

    try {
      List<Long> allResponseTime = new ArrayList<>();
      executor.invokeAll(callables).stream()
          .map(
              future -> {
                try {
                  return future.get();
                } catch (Exception e) {
                  throw new IllegalStateException(e);
                }
              })
          .forEach(allResponseTime::addAll);
      System.out.println(
          String.format(
              "平均响应时间是：%f",
              allResponseTime.stream().mapToDouble(d -> d).average().orElse(Double.NaN)));
      System.out.println(
          String.format(
              "95%% 响应时间是：%d",
              (long) allResponseTime.stream().sorted().toArray()[(int) Math.ceil(100 * 0.95)]));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      System.out.println("attempt to shutdown executor");
      executor.shutdown();
      executor.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.err.println("tasks interrupted");
    } finally {
      if (!executor.isTerminated()) {
        System.err.println("cancel non-finished tasks");
      }
      executor.shutdownNow();
      System.out.println("shutdown finished");
    }
  }

  @Test
  public void testPerformance() {
    StressTest stressTest = new StressTest("https://www.baidu.com", 100, 10);
    stressTest.run();
  }
}
