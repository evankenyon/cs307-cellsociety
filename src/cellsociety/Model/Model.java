package cellsociety.Model;

import cellsociety.Rule.RulesInterface;
import cellsociety.cell.Cell;
import cellsociety.Rule.GameOfLifeRules;

import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.Node;
import java.util.List;
import java.util.ArrayList;

public class Model {

  // Model.class.getPackageName() code borrowed from lab_reflection course gitlab repo
  private static final String DEFAULT_RESOURCE_PACKAGE =
      Model.class.getPackageName() + ".resources.";
  private static final String NUM_CORNERS_FILENAME = "numCorners";
  Cell[][] cellGrid;
  String simulationType;
  private ResourceBundle numCorners;
  private HashMap<Integer, List<Cell>> modelStateMap;


  public Model() {
    numCorners = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + NUM_CORNERS_FILENAME);
    // TODO: implement this properly
    simulationType = "GameOfLife";
    cellGrid = null;
  }

  public void setCellGrid(List<Cell> cellList, int rows, int cols){
    cellGrid = new Cell[rows][cols];
    for(Cell cell: cellList){
      int rowPosition = cell.getiIndex();
      int colPosition = cell.getjIndex();
      cellGrid[rowPosition][colPosition] = cell;
    }

  }
  public void setCellGrid(Cell[][] cellGrid) {
    this.cellGrid = cellGrid;
    updateAllNeighbors();
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
        RulesInterface r = new GameOfLifeRules(cellGrid[row][col]);
        //TODO: Implement properly
        try {
          r.setState();
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


  public void updateAllCellStates(){
    for (int row = 0; row < cellGrid.length; row++) {
      for (int col = 0; col < cellGrid[0].length; col++) {
        cellGrid[row][col].updateState();
      }
    }
  }

  public void updateAllCellNeighborMaps(){
    for (int row = 0; row < cellGrid.length; row++) {
      for (int col = 0; col < cellGrid[0].length; col++) {
        cellGrid[row][col].createNeighborStateMap();
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

  public void createModelStateMap(){
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
