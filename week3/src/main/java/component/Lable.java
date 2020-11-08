package component;

import javax.swing.*;
import java.awt.*;

public class Lable extends QComponent {
  public Lable(String name) {
    super(name);
  }

  public Lable(String name, int x, int y, int width, int height) {
    super(name, x, y, width, height);
  }

  public Component render() {
    JLabel label = new JLabel(this.name);

    return label;
  }
}
