package component;

import javax.swing.*;
import java.awt.*;

public class Picture extends QComponent {

  public Picture(String name, int x, int y, int width, int height) {
    super(name, x, y, width, height);
  }

  public Component render() {
    JPanel picture = new JPanel();
    picture.setLayout(null);
    picture.setBackground(Color.CYAN);
    JLabel picLabel = new JLabel("logo");
    picLabel.setBounds(140, 10, 80, 25);
    picture.add(picLabel);

    return picture;
  }
}
