package cellsociety.Model;

import cellsociety.Rule.RulesInterface;
import cellsociety.cell.Cell;

import cellsociety.cell.IllegalCellStateException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javafx.scene.Node;

public class Model {

  // Model.class.getPackageName() code borrowed from lab_reflection course gitlab repo
  private static final String DEFAULT_RESOURCE_PACKAGE =
      Model.class.getPackageName() + ".resources.";
  private static final String NUM_CORNERS_FILENAME = "NumCorners";
  private static final String VALUE_ALTERNATIVES_FILENAME = "ValuesAlternatives";
  private static final String KEY_ALTERNATIVES_FILENAME = "KeyAlternatives";
  private List<Cell> cellList;
  private Map<String, String> simulationInfo;
  //  private String simulationType;
  private ResourceBundle numCorners;
  private ResourceBundle valueAlternatives;
  private ResourceBundle keyAlternatives;
  private int rows;
  private int cols;
  private HashMap<Integer, List<Cell>> modelStateMap;


  public Model() {
    numCorners = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + NUM_CORNERS_FILENAME);
    valueAlternatives = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + VALUE_ALTERNATIVES_FILENAME);
    keyAlternatives = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + KEY_ALTERNATIVES_FILENAME);
    simulationInfo = new HashMap<>();
    cellList = new ArrayList<>();
  }

  public void setupCells(List<Integer> cellStateList, int rows, int cols)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException {
    this.rows = rows;
    this.cols = cols;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        try {
          cellList.add(new Cell(row, col, cellStateList.get(rows * row + col), rows, cols, simulationInfo.get("Shape")));
        } catch (NullPointerException e) {
          cellList.add(new Cell(row, col, cellStateList.get(rows * row + col), rows, cols, "Rectangle"));
        }
      }
    }
    updateAllNeighborsList();
    affectAllCells("updateCellNeighborStateMap");
  }

  public void updateModel()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    affectAllCells("updateState");
    affectAllCells("updateCellNeighborStateMap");
    createModelStateMap();
  }


  private void updateAllNeighborsList() {
    for (Cell cell : cellList) {
      updateSingleCellNeighbors(cell);
    }
  }

  private void affectAllCells(String method)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    for (Cell cell : cellList) {
      Method cellMethod = cell.getClass().getDeclaredMethod(method);
      cellMethod.invoke(cell);
    }
  }

  public void changeShapeOfCells(String shape) {
    for (Cell cell : cellList) {

      cell.changeShape(shape, rows, cols);
    }
    updateAllNeighborsList();
  }


  public void setSimulationInfo(Properties simulationInfo) {
    // Borrowed code to loop through props keys and values from
    // https://www.boraji.com/how-to-iterate-properites-in-java
    for (String key : simulationInfo.stringPropertyNames()) {
//      System.out.println(key);

      if (keyAlternatives.containsKey(key)) {
        try {
          this.simulationInfo.put(keyAlternatives.getString(key),
              valueAlternatives.getString(simulationInfo.getProperty(key)));
        } catch (MissingResourceException e) {
          this.simulationInfo.put(keyAlternatives.getString(key), simulationInfo.getProperty(key));
        }
      }
    }
  }

  private List<Double> createParamsDoubleList(String params) {
    List<Double> paramsList = new ArrayList<>();
    try {
      for (String param : params.split(",")) {
        paramsList.add(Double.parseDouble(param));
      }
    } catch (NullPointerException e) {
      return new ArrayList<>();
    }

    return paramsList;
  }

  public Cell getCell(int row, int col) {
    return cellList.get(rows*row + col);
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  private void updateSingleCellNeighbors(Cell inputCell) {
    for (Cell cell : cellList) {
      inputCell.updateNeighbors(cell,
          parseNumCornersList(numCorners.getString(simulationInfo.get("Type"))));
    }
  }

  private List<Integer> parseNumCornersList(String numCorners) {
    String[] numCornersStringArr = numCorners.split(",");
    List<Integer> numCornersList = new ArrayList<>();
    for (String numCorner : numCornersStringArr) {
      numCornersList.add(Integer.parseInt(numCorner));
    }
    return numCornersList;
  }

  /**
   * find what the next state should be for each cell
   */
  public void findNextStateForEachCell()
      throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, IllegalCellStateException {
    for (Cell cell : cellList) {
      cell.updateCellNeighborStateMap();
      RulesInterface r = (RulesInterface) Class.forName(
              String.format("%s%sRules", numCorners.getString("RulesPackageName"),
                  simulationInfo.get("Type")))
          .getConstructor(Cell.class, List.class)
          .newInstance(cell, createParamsDoubleList(simulationInfo.get("Parameters")));

      r.setState();
    }

  }

  public void createModelStateMap() {
    modelStateMap = new HashMap<>();
    for (Cell cell : cellList) {
      int cellCurrentState = cell.getCurrentState();
      modelStateMap.putIfAbsent(cellCurrentState, new ArrayList<>());
      modelStateMap.get(cellCurrentState).add(cell);
    }
  }

  public String getSimulationType() {
    return simulationInfo.get("Type");
  }

}
