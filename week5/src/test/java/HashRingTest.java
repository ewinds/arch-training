import com.google.common.util.concurrent.AtomicLongMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HashRingTest {
  private double getStandardDeviation(Long[] array) {
    int sum = 0;
    for (int i = 0; i < array.length; i++) {
      sum += array[i]; // 求出数组的总和
    }

    double average = sum / array.length; // 求出数组的平均数
    int total = 0;

    for (int i = 0; i < array.length; i++) {
      total += (array[i] - average) * (array[i] - average); // 求出方差，如果要计算方差的话这一步就可以了
    }

    double standardDeviation = Math.sqrt(total / array.length); // 求出标准差

    return standardDeviation;
  }

  @Test
  public void testNodes() {
    HashGenerator hasher = new MD5HashGenerator();
    HashRing hashRing = new HashRing(hasher, 100);

    for (int i = 0; i < 3; i++) {
      Node node = new Node(String.format("node%d", i));
      hashRing.addNode(node);
    }

    System.out.println(hashRing.toString());
  }

  @Test
  public void testStandardDeviation() {
    for (int i = 0; i < 20; i++) {
      testOnce(i);
    }
  }

  private void testOnce(int order) {
    HashGenerator hasher = new MD5HashGenerator();
    HashRing hashRing = new HashRing(hasher, 100);

    for (int i = 0; i < 10; i++) {
      Node node = new Node(String.format("node%d", i));
      hashRing.addNode(node);
    }

    // 构造 1000000 随机请求
    List<String> keys = new ArrayList<>();

    for (int i = 0; i < 1000000; i++) {
      keys.add(UUID.randomUUID().toString());
    }

    AtomicLongMap<String> atomicLongMap = AtomicLongMap.create();

    for (String key : keys) {
      atomicLongMap.getAndIncrement(hashRing.route(key));
    }

    System.out.println(
        String.format(
            "第%d次测试,标准差是：%f",
            order + 1,
            getStandardDeviation(atomicLongMap.asMap().values().toArray(new Long[] {}))));
  }
}
