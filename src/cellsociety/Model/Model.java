package cellsociety.Model;

import cellsociety.Rule.RulesInterface;
import cellsociety.cell.Cell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javafx.scene.Node;

public class Model {

  // Model.class.getPackageName() code borrowed from lab_reflection course gitlab repo
  private static final String DEFAULT_RESOURCE_PACKAGE =
      Model.class.getPackageName() + ".resources.";
  private static final String NUM_CORNERS_FILENAME = "NumCorners";
  private static final String KEY_VALUE_ALTERNATIVES_FILENAME = "KeyValueAlternatives";
  private Cell[][] cellGrid;
  private List<Cell> cellList;
  private String simulationType;
  private ResourceBundle numCorners;
  private ResourceBundle keyValueAlternatives;
  private int rows;
  private int cols;
  private HashMap<Integer, List<Cell>> modelStateMap;



  public Model() {
    numCorners = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + NUM_CORNERS_FILENAME);
    keyValueAlternatives = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + KEY_VALUE_ALTERNATIVES_FILENAME);
    // TODO: implement this properly

  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }

  public void setCellList(List<Cell> cellList) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    this.cellList = cellList;
    updateAllNeighborsList();

//    updateAllCellNeighborMaps();
    affectAllCells("updateCellNeighborStates");

  }

  public void updateModel() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//    updateAllCellStates();
    affectAllCells("updateState");

//    updateAllCellNeighborMaps();
    affectAllCells("updateCellNeighborStateMap");

    createModelStateMap();
  }


  private void updateAllNeighborsList() {
    for(Cell cell: cellList){
      updateSingleCellNeighbors(cell);
    }
  }


  private void updateAllCellStates() {
    for(Cell cell: cellList){
      cell.updateState();
    }
  }

  private void updateAllCellNeighborMaps(){
    for(Cell cell: cellList){
      cell.updateCellNeighborStateMap();
    }

  }


  private void affectAllCells(String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    for(Cell cell: cellList) {
      Method realMethod = cell.getClass().getDeclaredMethod(method);
      realMethod.invoke(cell);
    }
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

  public List<Cell> getCellList() {
    return cellList;
  }

  public Cell[][] getCellGrid() {
    return cellGrid;
  }

  private void updateSingleCellNeighbors(Cell inputCell){
    for(Cell cell: cellList){
      inputCell.updateNeighbors(cell, Integer.parseInt(numCorners.getString(simulationType)));
    }
  }

  /**
   * find what the next state should be for each cell
   */
  public void findNextStateForEachCell() {
    for(Cell cell: cellList){
      RulesInterface r = null;
      try {
        r = (RulesInterface) Class.forName(
                        String.format("%s%sRules", numCorners.getString("RulesPackageName"), simulationType))
                .getConstructor(Cell.class, List.class).newInstance(cell, new ArrayList<>());
      } catch (Exception e) {
        //TODO: Implement properly
        e.printStackTrace();
      }
      //TODO: Implement properly
      try {
        r.setState();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * We shoudl remove this method eventually
   * get a list of all the nodes to go on screen, representing displays of each cell
   * @return a list of nodes displaying the cells
   */
  public List<Node> getCellDisplays() {
    List<Node> nodeList = new ArrayList<>();
    for(Cell cell: cellList){
      nodeList.add(cell.getMyDisplay());
    }
    return nodeList;
  }

  /**
   * get a list of all the nodes to go on screen, representing displays of each cell
   * @return a list of nodes displaying the cells
   */
  public List<Cell> getCells() {
    return cellList;
  }

  public void createModelStateMap() {
    modelStateMap = new HashMap<>();
    for(Cell cell: cellList){
      int cellCurrentState = cell.getCurrentState();
      modelStateMap.putIfAbsent(cellCurrentState, new ArrayList<>());
      modelStateMap.get(cellCurrentState).add(cell);
    }
  }


  public int[] getGridShape(){
    int[] shape = {rows, cols};
    return shape;
  }

  public String getSimulationType(){
    return simulationType;
  }

}
