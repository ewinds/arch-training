package component;

import javax.swing.*;
import java.awt.*;

public class CheckBox extends QComponent {
  public CheckBox(String name) {
    super(name);
  }

  public CheckBox(String name, int x, int y, int width, int height) {
    super(name, x, y, width, height);
  }

  public Component render() {
    JCheckBox checkBox = new JCheckBox();

    return checkBox;
  }
}
