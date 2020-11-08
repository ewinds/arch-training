package component;

import javax.swing.*;
import java.awt.*;

public class TextBox extends QComponent {
  public TextBox(String name) {
    super(name);
  }

  public TextBox(String name, int x, int y, int width, int height) {
    super(name, x, y, width, height);
  }

  public Component render() {
    JTextField textField = new JTextField(20);

    return textField;
  }
}
