package component;

import javax.swing.*;
import java.awt.*;

public class WinForm extends QContainer {

  public WinForm(String name) {
    super(name);

  }

  public Component render() {
    JFrame frame = new JFrame("Login Example");
    frame.setSize(350, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.GREEN);
    frame.add(panel);
    frame.setVisible(true);

    return panel;
  }
}
