package cellsociety.cell;


import cellsociety.Model.Model;
import cellsociety.location.CornerLocation;

import java.util.*;
import javafx.scene.Node;

import cellsociety.CornerLocationGenerator.RectangleCellCornerLocationGenerator;
import cellsociety.location.CornerLocation;

import java.util.stream.Collectors;


public class Cell {
  private static final String DEFAULT_RESOURCE_PACKAGE =
      Cell.class.getPackageName() + ".resources.";
  private static final String DEFAULT_VALUES_FILENAME = "DefaultValues";

  private int currentState;
  private int futureState;
  private List<Cell> neighbors;
  private List<CornerLocation> corners;
  private final int iIndex;
  private final int jIndex;
  private Map<Integer, List<Cell>> neighborCellStateMap;
  private CellDisplay myDisplay;
  private int chrononCounter;
  private int energy;


  public Cell(int i, int j, int initialState, int rows, int columns) {
    ResourceBundle defaultVals = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + DEFAULT_VALUES_FILENAME);
    this.iIndex = i;
    this.jIndex = j;
    this.currentState = initialState;
    corners = new RectangleCellCornerLocationGenerator(rows, columns).generateCorners(i, j);
    neighbors = new ArrayList<>();
    myDisplay = new CellDisplay(j * Integer.parseInt(defaultVals.getString("defaultWidth")),
        i * Integer.parseInt(defaultVals.getString("defaultHeight")), currentState);
    neighborCellStateMap = new HashMap<>();
    chrononCounter = 0;
    energy = 5;
  }



  /**
   * set what myDisplay refers to
   *
   * @param disp will become myDisplay
   */
  public void setDisplay(CellDisplay disp) {
    myDisplay = disp;
  }

  public int getjIndex() {
    return jIndex;
  }

  public int getiIndex() {
    return iIndex;
  }


  public void updateState() {
    currentState = futureState;
    myDisplay.changeState(currentState);
  }


  public void setFutureState(int state) {
    this.futureState = state;
  }

  public int getCurrentState() {
    return currentState;
  }

  public int getFutureState() {
    return futureState;
  }

  public List<CornerLocation> getCorners() {
    return corners;
  }

  public void updateNeighbors(Cell potentialNeighbor, List<Integer> numCornersShared) {
    Set<CornerLocation> sharedCorners = corners.stream()
        .distinct()
        .filter(potentialNeighbor.corners::contains)
        .collect(Collectors.toSet());
    if (numCornersShared.contains(sharedCorners.size()) && !neighbors.contains(potentialNeighbor)
        && !potentialNeighbor.equals(this)) {
      neighbors.add(potentialNeighbor);
    }
  }

  public int numOfStateNeighbors(int state) {
    if (neighborCellStateMap.containsKey(state)) {
      return neighborCellStateMap.get(state).size();
    }
    return 0;
  }

  public void updateCellNeighborStateMap() {
    neighborCellStateMap = new HashMap<>();
    for (Cell neighbor : neighbors) {
      int state = neighbor.getCurrentState();
      neighborCellStateMap.putIfAbsent(state, new ArrayList<>());
      neighborCellStateMap.get(state).add(neighbor);
    }
  }

  public int getNumNeighbors() {
    return neighbors.size();
  }

  // TODO: Remove
  public void setNeighbors(List<Cell> neighbors) {
    this.neighbors = neighbors;
  }


  public Cell getNeighborOfState(int state, int num) {
    return neighborCellStateMap.get(state).get(num);
  }


  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    Cell cell = (Cell) o;
    return currentState == cell.currentState && futureState == cell.futureState
        && iIndex == cell.iIndex && jIndex == cell.jIndex && Objects.equals(neighbors,
        cell.neighbors) && Objects.equals(corners, cell.corners) && Objects.equals(
        neighborCellStateMap, cell.neighborCellStateMap) && Objects.equals(myDisplay,
        cell.myDisplay);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentState, futureState, neighbors, corners, iIndex, jIndex,
        neighborCellStateMap, myDisplay);
  }

  public Node getMyDisplay() {
    return myDisplay.getMyDisplay();
  }

  public void updateChronon() {
    chrononCounter++;
  }

  public void resetChronon() {
    chrononCounter = 0;
  }

  public void gainEnergy() {
    energy++;
  }

  public void loseEnergy() {
    energy--;
  }


  //Testing Purposes
  public void setChrononCounter(int chrononCounter) {
    this.chrononCounter = chrononCounter;
  }

  public int getChrononCounter() {
    return chrononCounter;
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = energy;
  }

}
