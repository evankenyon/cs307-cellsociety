package cellsociety.view;

import cellsociety.cell.ImmutableCell;
import cellsociety.resourceHandlers.LanguageResourceHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import cellsociety.cell.Cell;
import cellsociety.cell.CellDisplay;
import cellsociety.resourceHandlers.ViewResourceHandler;
import cellsociety.controller.Controller;

import cellsociety.CornerLocationGenerator.RectangleCellCornerLocationGenerator;
import cellsociety.CornerLocationGenerator.CornerLocationGenerator;
import cellsociety.CornerLocationGenerator.HexagonCellCornerLocationGenerator;
import cellsociety.location.CornerLocation;

/**
 * Objects of this class represent the grid display for a simulation
 * @author Keith Cressman
 */
public class CellGridDisplay extends ChangeableDisplay{
  private Controller myController;
  private ViewResourceHandler myViewResourceHandler;
  private CornerLocationGenerator myCornerGenerator;
  private List<CellDisplay> allCellDisplays;
  private Group myGroup;

  /**
   * create a CellGridDisplay
   * @param controller will be used to get things like grid shape and a list of cells.
   *                   I don't love the idea of making myController an instance variable here but it's convenient -Keith
   */
  public CellGridDisplay(Controller controller){
    allCellDisplays = new ArrayList<>();
    myViewResourceHandler = new ViewResourceHandler();
    myController = controller;
    myCornerGenerator = new HexagonCellCornerLocationGenerator(controller.getGridShape()[0], controller.getGridShape()[1]);
  }

  /**
   * create the node to go on the scene, with the entire grid display
   * @return a node displaying all the cells in their grid
   */
  public Node createGridDisplay(){
    //make a Node containing a background and all the cell displays on top of it
    myGroup = new Group();
    Rectangle r = new Rectangle();
    myGroup.getChildren().add(new Rectangle(0, 0, myViewResourceHandler.simulationWidth(), myViewResourceHandler.simulationWidth()));
    myCornerGenerator = new RectangleCellCornerLocationGenerator(myController.getGridShape()[0], myController.getGridShape()[1]);
    myGroup.getChildren().addAll(makeCellDisplays());
    myDisp = myGroup;
    return myGroup;
  }

  private List<Node> makeCellDisplays(){
    //take all the cells in the simulation and create a cell display for them,

    List<Node> displayNodes = new ArrayList<>();
    List<Cell> allCells = new ArrayList<>();
    for(int row = 0; row < myController.getGridShape()[0]; row++) {
      for(int col = 0; col < myController.getGridShape()[1]; col++) {
        allCells.add(myController.getCell(row, col));
      }
    }
    int[] gridShape = myController.getGridShape();
    int numRows = gridShape[0];
    int numCols = gridShape[1];
    double widthPerCell = myViewResourceHandler.simulationWidth() / (numCols + 0.01);
    double heightPerCell = myViewResourceHandler.simulationWidth() / (numRows + 0.01);
    for (Cell cell : allCells){
      displayNodes.add(makeACellDisplay(cell, widthPerCell, heightPerCell).getMyDisplay());
    }
    return displayNodes;
  }

  private CellDisplay makeACellDisplay(ImmutableCell cell, double widthPerCell, double heightPerCell){
    //make a cell display for the cell with the width and height given as arguments
    // CellDisplay newDisplay = new CellDisplay(cell.getjIndex() * widthPerCell,
    //cell.getiIndex() * heightPerCell, widthPerCell, heightPerCell, cell.getCurrentState());
    CellDisplay newDisplay = new CellDisplay(generateXYs(cell), cell.getCurrentState(), myController);
    newDisplay.setCell(cell);
    newDisplay.setColors(myViewResourceHandler.getColorsForSimulation(
        myController.getSimulationType()));
    allCellDisplays.add(newDisplay);
    myController.addCellObserver(cell.getiIndex(), cell.getjIndex(), cellState -> newDisplay.changeState(cellState));
    return newDisplay;
  }

  private double[] generateXYs(ImmutableCell cell){
    List<CornerLocation> locations = cell.getCorners();
    double[] retXYs = new double[2*locations.size()];
    int index = 0;
    for (CornerLocation corner : locations){
      retXYs[2*index] = corner.getX_pos();
      retXYs[2*index + 1] = corner.getY_pos();
      index = index + 1;
    }
    return retXYs;

  }

  /**
   * change the cell shapes to the specified argument, which must be a valid shape
   * @param newShape is a String like "Triangle" or "Rectangle" that corresponds to a valid cell shape
   */
  public void changeCellShapes(String newShape){
    myCornerGenerator = myViewResourceHandler.getCornerLocationGenerator(newShape, myController.getGridShape()[0],
        myController.getGridShape()[1]);
    try {
      myController.changeShape(newShape);
    } catch (IllegalAccessException | InvocationTargetException e) {
      displayErrorMessage(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.FAILED_REFLECT_KEY));
    }

    removeCellDisplays();
    myGroup.getChildren().addAll(makeCellDisplays());
  }

  private void removeCellDisplays(){
    //remove all the cell displays from the scene (remove from myGroup) and remove them from allCellDisplays
    List<CellDisplay> oldDisplays = new ArrayList<>();
    for (CellDisplay cellDisp : allCellDisplays){
      myGroup.getChildren().remove(cellDisp.getMyDisplay());
      oldDisplays.add(cellDisp);
    }
    allCellDisplays.removeAll(oldDisplays);
  }

  /**
   * get a list of all the cell displays in the simulation
   * @return allCellDisplays
   */
  public List<CellDisplay> getAllCellDisplays(){
    return Collections.unmodifiableList(allCellDisplays);
  }
}
