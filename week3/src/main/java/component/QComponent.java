package component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class QComponent implements Printable {
  String name;
  int x;
  int y;
  int width;
  int height;
  List<Printable> components = new ArrayList<Printable>();

  public QComponent(String name) {
    this.name = name;
  }

  public QComponent(String name, int x, int y, int width, int height) {
    this.name = name;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  private void log() {
    System.out.println(String.format("print %s(%s)", this.getClass().getSimpleName(), this.name));
  }

  public Component print() {
    this.log();
    Component component = this.render();

    if (width > 0) {
      component.setBounds(x, y, width, height);
    }

    return component;
  }

  public Printable add(Printable component) {
    components.add(component);

    return this;
  }
}
