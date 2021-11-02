package cellsociety.view;
import javafx.scene.control.Label;

/**
 * Objects of this class act like any other label. I made a separate class for it to enable changing language dynamically - Keith
 * @author Keith Cressman
 */
public class Label2 extends Label implements NodeWithText{


  public Label2(String text){
    super(text);
  }


}
