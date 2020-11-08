package component;

import java.awt.*;

public abstract class QContainer extends QComponent {
  public QContainer(String name) {
    super(name);
  }

  public QContainer(String name, int x, int y, int width, int height) {
    super(name, x, y, width, height);
  }

  @Override
  public Component print() {
    Component component = super.print();

    if (this.components.size() > 0) {
      Container container = (Container) component;

      for (Printable p : components) {
        Component c = p.print();

        if (c != null) {
          container.add(c);
        }
      }
    }

    return component;
  }
}
