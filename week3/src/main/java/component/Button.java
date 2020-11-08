package component;

import javax.swing.*;
import java.awt.*;

public class Button extends QComponent {

  public Button(String name, int x, int y, int width, int height) {
    super(name, x, y, width, height);
  }

  public Component render() {
    JButton button = new JButton(this.name);

    return button;
  }
}
