package cellsociety.cell;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class CellDisplay {
  private Node myDisp;
  public CellDisplay(int x, int y, int width, int height, int state){
    myDisp = new Rectangle(x, y, width, height);
    changeState(state);
  }

  public Node getMyDisplay(){
    return myDisp;
  }

  /**
   * change the display based on state of the cell
   * @param state
   */
  public void changeState(int state){
    if (state == 0) {
      ((Rectangle) myDisp).setFill(Color.WHITE);
    }
    else{
      ((Rectangle) myDisp).setFill(Color.BLUE);
    }
  }
}
