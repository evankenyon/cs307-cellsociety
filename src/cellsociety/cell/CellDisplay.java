package cellsociety.cell;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class CellDisplay {
  private Node myDisp;
  private Cell myCell;
  private int myState;
  public static final Color ON_COLOR = Color.BLUE;
  public static final Color OFF_COLOR = Color.BLACK;
  private Color[] stateColors = {Color.BLACK, Color.RED, Color.GREEN};

  public CellDisplay(double x, double y, int state){
    this(x, y, Cell.DEFAULT_WIDTH, Cell.DEFAULT_HEIGHT, state);
  }

  public CellDisplay(double x, double y, double width, double height, int state){
    myDisp = new Rectangle(x, y, width, height);
    myDisp.setOnMouseClicked(e -> cellClicked());
    myState = state;
    changeState(state);
  }



  /**
   * set what cell corresponds to this cell display
   * @param cell will be myCell
   */
  public void setCell(Cell cell){
    myCell = cell;
  }

  private void cellClicked(){
    //handle what happens when a cell is clicked on in the GUI
    int nextState = (myCell.getCurrentState() + 1) % stateColors.length;
    myCell.setFutureState(nextState);
    myCell.updateState();
  }

  /**
   * change the color corresponding to each state
   * @param colors is an array where colors[i] is the color for the ith state in the simulation
   */
  public void setColors(Color[] colors){
    stateColors = colors;
    changeState(myState);
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
    state = state % stateColors.length;
    myState = state;
    ((Rectangle) myDisp).setFill(stateColors[state]);

  }

  /**
   * get the array of possible colors
   * @return stateColors
   */
  public Color[] getStateColors(){
    return stateColors;
  }

  /**
   * get the state of this cellDisplay
   * @return myState
   */
  public int getState(){
    return myState;
  }

  /**
   * get the color of this cell display
   * @return stateColors[myState]
   */
  public Color getMyColor(){
    return stateColors[myState];
  }



}
