package cellsociety.Model;

import cellsociety.Rule.RulesInterface;
import cellsociety.cell.Cell;

import java.util.*;

import javafx.scene.Node;

public class Model {

  // Model.class.getPackageName() code borrowed from lab_reflection course gitlab repo
  private static final String DEFAULT_RESOURCE_PACKAGE =
      Model.class.getPackageName() + ".resources.";
  private static final String NUM_CORNERS_FILENAME = "NumCorners";
  private static final String KEY_VALUE_ALTERNATIVES_FILENAME = "KeyValueAlternatives";
  private Cell[][] cellGrid;
  private String simulationType;
  private ResourceBundle numCorners;
  private ResourceBundle keyValueAlternatives;
  private HashMap<Integer, List<Cell>> modelStateMap;


  public Model() {
    numCorners = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + NUM_CORNERS_FILENAME);
    keyValueAlternatives = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + KEY_VALUE_ALTERNATIVES_FILENAME);
    // TODO: implement this properly
    cellGrid = null;
  }

  public void setSimulationInfo(Properties simulationInfo) {
    // Borrowed code to loop through props keys and values from
    // https://www.boraji.com/how-to-iterate-properites-in-java
    for (String key : simulationInfo.stringPropertyNames()) {
      if (keyValueAlternatives.containsKey(key)) {
        simulationType = keyValueAlternatives.getString(simulationInfo.getProperty(key));
      }
    }
  }

  public void setCellGrid(List<Cell> cellList, int rows, int cols) {
    cellGrid = new Cell[rows][cols];
    for (Cell cell : cellList) {
      int rowPosition = cell.getiIndex();
      int colPosition = cell.getjIndex();
      cellGrid[rowPosition][colPosition] = cell;
    }

  }

  public void setCellGrid(Cell[][] cellGrid) {
    this.cellGrid = cellGrid;
    updateAllNeighbors();
    updateAllCellNeighborMaps();
  }

  public Cell[][] getCellGrid() {
    return cellGrid;
  }

  private void updateAllNeighbors() {
    for (int row = 0; row < cellGrid.length; row++) {
      for (int col = 0; col < cellGrid[0].length; col++) {
        Cell cell = cellGrid[row][col];
        updateSingleCellNeighbors(cell);
      }
    }
  }

  private void updateSingleCellNeighbors(Cell cell) {
    for (int checkAgainstRow = 0; checkAgainstRow < cellGrid.length; checkAgainstRow++) {
      for (int checkAgainstCol = 0; checkAgainstCol < cellGrid[0].length; checkAgainstCol++) {
        cell.updateNeighbors(cellGrid[checkAgainstRow][checkAgainstCol],
            Integer.parseInt(numCorners.getString(simulationType)));
      }
    }
  }

  /**
   * find what the next state should be for each cell
   */
  public void findNextStateForEachCell() {
    for (int row = 0; row < cellGrid.length; row++) {
      for (int col = 0; col < cellGrid[0].length; col++) {
        RulesInterface r = null;
        try {
          r = (RulesInterface) Class.forName(
                  String.format("%s%sRules", numCorners.getString("RulesPackageName"), simulationType))
              .getConstructor(Cell.class).newInstance(cellGrid[row][col]);
        } catch (Exception e) {
          //TODO: Implement properly
          e.printStackTrace();
        }
        //TODO: Implement properly
        try {
          r.setState();
          updateCellMovement(cellGrid[row][col]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * loop through each cell, set its future state
   */
  public void updateModel() {
    updateAllCellStates();
    updateAllCellNeighborMaps();
    createModelStateMap();
  } //loop through each cell, set its current state to future state, calls updateCurrentStateMethod


  private void updateCellMovement(Cell cell)
  {
    // Can actually prolly move all this to cell class
    if(cell.isShouldMove())
    {
      int state=cell.getCompareState();
      Random random=new Random();
      int randInt= random.nextInt(cell.numOfStateNeighbors(state)-1);
      cell.getNeighborCellStateMap().get(state).get(randInt).setFutureState(cell.getCurrentState());
      cell.setShouldMove(false);
    }
  }

  private void updateAllCellStates() {
    for (int row = 0; row < cellGrid.length; row++) {
      for (int col = 0; col < cellGrid[0].length; col++) {
        cellGrid[row][col].updateState();
      }
    }
  }

  private void updateAllCellNeighborMaps() {
    for (int row = 0; row < cellGrid.length; row++) {
      for (int col = 0; col < cellGrid[0].length; col++) {
        cellGrid[row][col].updateCellNeighborStates();
      }
    }
  }

  /**
   * get a list of all the nodes to go on screen, representing displays of each cell
   *
   * @return a list of nodes displaying the cells
   */
  public List<Node> getCellDisplays() {
    List<Node> nodeList = new ArrayList<>();
    for (int row = 0; row < cellGrid.length; row++) {
      for (int col = 0; col < cellGrid[0].length; col++) {
        nodeList.add(cellGrid[row][col].getMyDisplay());
      }
    }
    return nodeList;
  }

  public void createModelStateMap() {
    modelStateMap = new HashMap<>();
    for (int row = 0; row < cellGrid.length; row++) {
      for (int col = 0; col < cellGrid[0].length; col++) {
        Cell cell = cellGrid[row][col];
        int cellCurrentState = cell.getCurrentState();
        modelStateMap.putIfAbsent(cellCurrentState, new ArrayList<>());
        modelStateMap.get(cellCurrentState).add(cell);
      }
    }
  }

}
