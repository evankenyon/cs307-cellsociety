package view;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.shape.Polygon;
import javafx.scene.Node;


import cellsociety.view.CellGridDisplay;
import cellsociety.controller.Controller;
import cellsociety.cell.CellDisplay;

import java.io.File;
import java.util.List;
import java.util.Collection;

public class CellGridDisplayTest {
  private CellGridDisplay myGridDisplay;
  private Controller myController;

  @BeforeEach
  void setup(){
    myController = new Controller();
    try {
      myController.parseFile(new File(SimulationDisplayTest.BLINKERS_PATH));
    }catch(Exception e){
      //shouldn't be possible
    }
    myGridDisplay = new CellGridDisplay(myController);
    myGridDisplay.createGridDisplay();
  }
  @Test
  void testSetup(){
    //test that it made rectangular cell displays
    List<CellDisplay> displays = myGridDisplay.getAllCellDisplays();
    CellDisplay randomDisplay = displays.get(37);
    List<Double> points = ((Polygon)randomDisplay.getMyDisplay()).getPoints();
    assertEquals(8, points.size());
  }

  @Test
  void testChangeTriangle(){
    //test that it made rectangular cell displays
    myGridDisplay.changeCellShapes("Triangle");
    List<CellDisplay> displays = myGridDisplay.getAllCellDisplays();
    CellDisplay randomDisplay = displays.get(12);
    List<Double> points = ((Polygon)randomDisplay.getMyDisplay()).getPoints();
    assertEquals(6, points.size());
  }

  @Test
  void testChangeHexagon(){
    //test that it made rectangular cell displays
    myGridDisplay.changeCellShapes("Hexagon");
    List<CellDisplay> displays = myGridDisplay.getAllCellDisplays();
    CellDisplay randomDisplay = displays.get(57);
    List<Double> points = ((Polygon)randomDisplay.getMyDisplay()).getPoints();
    assertEquals(12, points.size());
  }

  @Test
  void testChangeShapesComplex(){
    //test that it made rectangular cell displays
    myGridDisplay.changeCellShapes("Hexagon");
    myGridDisplay.changeCellShapes("Triangle");
    myGridDisplay.changeCellShapes("Rectangle");
    myGridDisplay.changeCellShapes("Rectangle");
    myGridDisplay.changeCellShapes("Hexagon");
    List<CellDisplay> displays = myGridDisplay.getAllCellDisplays();
    CellDisplay randomDisplay = displays.get(80);
    List<Double> points = ((Polygon)randomDisplay.getMyDisplay()).getPoints();
    assertEquals(12, points.size());
  }

  @Test
  void testChangeShape(){

    myGridDisplay.changeCellShapes("Triangle");
  }


}
