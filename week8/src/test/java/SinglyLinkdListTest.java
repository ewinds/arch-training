import org.junit.Test;

public class SinglyLinkdListTest {
  @Test
  public void testOne() {
    SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<String>();
    singlyLinkedList.add("a");
    singlyLinkedList.add("b");
    singlyLinkedList.add("x");
    singlyLinkedList.add("y");
    singlyLinkedList.add("z");
    singlyLinkedList.addAfter("c", "b");
    singlyLinkedList.traverse();
  }

  private void findMergeElement(Node<String> nodeM, Node<String> nodeN) {
    SinglyLinkedList<String> listMerge = new SinglyLinkedList<String>();

    while (true) {
      if (nodeM == null && nodeN == null) {
        System.out.println("两个链表没有合并");
        break;
      }

      if (listMerge.contains(nodeM.getValue())) {
        System.out.println("找到合并元素：" + nodeM.getValue());
        break;
      } else if (listMerge.contains(nodeN.getValue())) {
        System.out.println("找到合并元素：" + nodeN.getValue());
        break;
      } else {
        listMerge.add(nodeM.getValue());
        listMerge.add(nodeN.getValue());
      }

      nodeM = nodeM.getNextRef();
      nodeN = nodeN.getNextRef();
    }

    System.out.println("时间复杂度：O(2n)");
  }

  @Test
  public void testFound() {
    SinglyLinkedList<String> listM = new SinglyLinkedList<String>();
    listM.add("a");
    listM.add("b");
    listM.add("x");
    listM.add("y");
    listM.add("z");

    SinglyLinkedList<String> listN = new SinglyLinkedList<String>();
    listN.add("d");
    listN.add("e");
    listN.add("f");
    listN.add("x");
    listN.add("y");
    listN.add("z");

    Node<String> nodeM = listM.getHead();
    Node<String> nodeN = listN.getHead();
    findMergeElement(nodeM, nodeN);
  }

  @Test
  public void testNotFound() {
    SinglyLinkedList<String> listM = new SinglyLinkedList<String>();
    listM.add("a");
    listM.add("b");
    listM.add("x");
    listM.add("y");
    listM.add("z");

    SinglyLinkedList<String> listN = new SinglyLinkedList<String>();
    listN.add("d");
    listN.add("e");
    listN.add("f");
    listN.add("g");
    listN.add("h");

    Node<String> nodeM = listM.getHead();
    Node<String> nodeN = listN.getHead();
    findMergeElement(nodeM, nodeN);
  }
}
