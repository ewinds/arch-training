import java.util.SortedMap;
import java.util.TreeMap;

public class HashRing {
  private SortedMap<Long, String> virtualNodes = new TreeMap<>();
  private HashGenerator hashGenerator;
  private int virtualNodeCount;

  public HashRing(HashGenerator hashGenerator, int virtualNodeCount) {
    this.hashGenerator = hashGenerator;
    this.virtualNodeCount = virtualNodeCount;
  }

  public void addNode(Node node) {
    String nodeName = node.getName();
    for (int i = 0; i < this.virtualNodeCount; i++) {
      String vName = String.format("%s-%d", nodeName, i);
      virtualNodes.put(hash(vName), nodeName);
    }
  }

  private long hash(String nodeName) {
    return this.hashGenerator.hash(nodeName);
  }

  @Override
  public String toString() {
    return this.virtualNodes.toString();
  }

  public String route(String key) {
    SortedMap<Long, String> toTailed = virtualNodes.tailMap(hashGenerator.hash(key));
    Long routeTo = toTailed.isEmpty() ? virtualNodes.firstKey() : toTailed.firstKey();

    return virtualNodes.get(routeTo);
  }
}
