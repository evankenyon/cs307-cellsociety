package cellsociety.view;

import javafx.scene.control.Button;

/**
 * Objects of this class act like any other button. I made a separate class for it to enable changing language dynamically - Keith
 * @author Keith Cressman
 */
public class Button2 extends Button implements NodeWithText {

  public Button2(String text) {
    super(text);
  }

}
