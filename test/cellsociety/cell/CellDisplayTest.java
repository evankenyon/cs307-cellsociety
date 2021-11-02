package cellsociety.cell;
import static org.junit.jupiter.api.Assertions.*;

import cellsociety.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class CellDisplayTest {
  private CellDisplay myDisplay;
  private int startX = 0;
  private int startY = 0;
  private int startState = 0;

  @BeforeEach
  void setup(){
    myDisplay = new CellDisplay(startX, startY, startState, new Controller());
  }

  @Test
  void testSetWidth(){
    myDisplay.setWidth(10);
    assertEquals(10, ((Rectangle)myDisplay.getMyDisplay()).getWidth());
  }

  @Test
  void testSetHeight(){
    myDisplay.setHeight(10);
    assertEquals(10, ((Rectangle)myDisplay.getMyDisplay()).getHeight());
  }


  @Test
  void testSetX(){
    myDisplay.setX(10);
    assertEquals(10, myDisplay.getMyDisplay().getLayoutX());
  }

  @Test
  void testSetY(){
    myDisplay.setY(10);
    assertEquals(10, myDisplay.getMyDisplay().getLayoutY());
  }

  @Test
  void testChangeState(){
    myDisplay.changeState(1);
    assertEquals(1, myDisplay.getState());
    myDisplay.changeState(2);
    assertEquals(0, myDisplay.getState());
  }

  @Test
  void testChangeStateWithColors(){

    myDisplay.changeState(1);
    assertEquals(Color.BLUE, myDisplay.getMyColor());
    myDisplay.changeState(2);
    assertEquals(Color.BLACK, myDisplay.getMyColor());
  }


}
