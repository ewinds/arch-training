package component;

import javax.swing.*;
import java.awt.*;

public class Frame extends QContainer {

  public Frame(String name, int x, int y, int width, int height) {
    super(name, x, y, width, height);
  }

  public Component render() {
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.YELLOW);

    return panel;
  }
}
