package cellsociety.cell;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class CellDisplay {
  private Node myDisp;
  public CellDisplay(int x, int y, int length){
    myDisp = new Rectangle(x, y, length, length);
  }

  public Node getMyDisplay(){
    return myDisp;
  }
}
