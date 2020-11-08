package component;

import javax.swing.*;
import java.awt.*;

public class PasswordBox extends QComponent {

  public PasswordBox(String name) {
    super(name);
  }

  public PasswordBox(String name, int x, int y, int width, int height) {
    super(name, x, y, width, height);
  }

  public Component render() {
    JPasswordField passwordText = new JPasswordField(20);

    return passwordText;
  }
}
