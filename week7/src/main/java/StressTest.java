import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StressTest {
  String url;
  int times;
  int concurrentCount;

  public StressTest(String url, int times, int concurrentCount) {
    this.url = url;
    this.times = times;
    this.concurrentCount = concurrentCount;
  }

  public void run() {
    int taskCount = times / concurrentCount;
    ExecutorService executor = Executors.newFixedThreadPool(concurrentCount);

    Callable<List<Long>> callable =
        () -> {
          List<Long> responseTimes = new ArrayList<>();
          OkHttpClient httpClient = new OkHttpClient();
          Request request = new Request.Builder().url(url).get().build();

          for (int i = 0; i < taskCount; i++) {
            Call call = httpClient.newCall(request);
            Response resp = null;

            try {
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
    List<Callable<List<Long>>> callables = Collections.nCopies(concurrentCount, callable);

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
              "平均响应时间是：%f 毫秒",
              allResponseTime.stream().mapToDouble(d -> d).average().orElse(Double.NaN)));
      System.out.println(
          String.format(
              "95%% 响应时间是：%d 毫秒",
              (long) allResponseTime.stream().sorted().toArray()[(int) Math.ceil(times * 0.95)]));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      executor.shutdown();
      executor.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.err.println("tasks interrupted");
    } finally {
      if (!executor.isTerminated()) {
        System.err.println("cancel non-finished tasks");
      }
      executor.shutdownNow();
    }
  }
}
