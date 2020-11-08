package component;

import java.awt.*;

public interface Printable {
    Component print();
    Printable add(Printable component);
    Component render();
}
