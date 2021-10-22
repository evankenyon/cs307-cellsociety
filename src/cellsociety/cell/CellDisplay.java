package cellsociety.cell;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class CellDisplay {
  private Node myDisp;
  public static final Color ON_COLOR = Color.BLUE;
  public static final Color OFF_COLOR = Color.BLACK;

  public CellDisplay(int x, int y, int state){
    myDisp = new Rectangle(x, y, Cell.DEFAULT_WIDTH, Cell.DEFAULT_HEIGHT);
    changeState(state);
  }

  /**
   * change width of the display
   * @param width will be the width of hte display
   */
  public void setWidth(int width){
    ((Rectangle)myDisp).setWidth(width);
  }


  /**
   * chagne the height of the display
   * @param height will be height of the display
   */
  public void setHeight(int height){
    ((Rectangle)myDisp).setHeight(height);
  }

  /**
   * change the x coordinate of display
   * @param x
   */
  public void setX(int x){
    myDisp.setLayoutX(x);
  }

  /**
   * change the y coordinate of display
   * @param y
   */
  public void setY(int y){
    myDisp.setLayoutY(y);
  }

  /**
   * get a node representing the display for this cell
   * @return the node (Rectangle) representing the cell
   */
  public Node getMyDisplay(){
    return myDisp;
  }

  /**
   * change the display based on state of the cell
   * @param state
   */
  public void changeState(int state){
    if (state == 0) {
      ((Rectangle) myDisp).setFill(OFF_COLOR);
    }
    else{
      ((Rectangle) myDisp).setFill(ON_COLOR);
    }
  }

}
