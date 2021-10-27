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
  private static final String VALUE_ALTERNATIVES_FILENAME = "ValuesAlternatives";
  private static final String KEY_ALTERNATIVES_FILENAME = "KeyAlternatives";
  private Cell[][] cellGrid;
  private List<Cell> cellList;
  private Map<String, String> simulationInfo;
//  private String simulationType;
  private ResourceBundle numCorners;
  private ResourceBundle valueAlternatives;
  private ResourceBundle keyAlternatives;
  private int rows;
  private int cols;
  private HashMap<Integer, List<Cell>> modelStateMap;
  private List<Double> args;



  public Model() {
    numCorners = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + NUM_CORNERS_FILENAME);
    valueAlternatives = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + VALUE_ALTERNATIVES_FILENAME);
    keyAlternatives = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + KEY_ALTERNATIVES_FILENAME);
    simulationInfo = new HashMap<>();
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }

  public void setCellList(List<Cell> cellList){
    this.cellList = cellList;
    updateAllNeighborsList();
    updateAllCellNeighborMaps();
  }


  private void updateAllNeighborsList() {
    for(Cell cell: cellList){
      updateSingleCellNeighbors(cell);
    }
  }

  public void updateModel() {
    updateAllCellStates();
    updateAllCellNeighborMaps();
    createModelStateMap();
  }

  private void updateAllCellStates() {
    for(Cell cell: cellList){
      cell.updateState();
    }
  }

  private void updateAllCellNeighborMaps(){
    for(Cell cell: cellList){
      cell.updateCellNeighborStates();
    }
  }


  public void setSimulationInfo(Properties simulationInfo) {
    // Borrowed code to loop through props keys and values from
    // https://www.boraji.com/how-to-iterate-properites-in-java
    for (String key : simulationInfo.stringPropertyNames()) {
//      System.out.println(key);
      if (keyAlternatives.containsKey(key)) {
        try {
          this.simulationInfo.put(keyAlternatives.getString(key), valueAlternatives.getString(simulationInfo.getProperty(key)));
        } catch (MissingResourceException e) {
          this.simulationInfo.put(keyAlternatives.getString(key), simulationInfo.getProperty(key));
        }
      }
    }
  }

  private List<Double> createParamsDoubleList(String params) {
    List<Double> paramsList = new ArrayList<>();
    for (String param : params.split(",")) {
      paramsList.add(Double.parseDouble(param));
    }
    return paramsList;
  }

  public List<Cell> getCellList() {
    return cellList;
  }

  public Cell[][] getCellGrid() {
    return cellGrid;
  }

  private void updateSingleCellNeighbors(Cell inputCell){
    for(Cell cell: cellList){
      inputCell.updateNeighbors(cell, Integer.parseInt(numCorners.getString(simulationInfo.get("Type"))));
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
                        String.format("%s%sRules", numCorners.getString("RulesPackageName"), simulationInfo.get("Type")))
                .getConstructor(Cell.class, List.class).newInstance(cell, createParamsDoubleList(simulationInfo.get("Parameters")));
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
    return simulationInfo.get("Type");
  }

}
