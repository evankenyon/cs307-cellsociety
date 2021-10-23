package cellsociety.Rule;


import cellsociety.cell.Cell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class GameOfLifeRulesInterface implements RulesInterface {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRulesInterface.class.getPackageName() + ".resources.";
  private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = GameOfLifeRulesInterface.class.getName();
  private Cell cell;
  private int cellCurrentState;
  private int numNeighbors;
  private ResourceBundle stateAndNeighborsMap;

  public GameOfLifeRulesInterface(Cell cell) {
    this.cell = cell;
    generateNumNeighbors();
    cellCurrentState = cell.getCurrentState();
    stateAndNeighborsMap = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + "GameOfLifeRules");
  }

  private void generateNumNeighbors() {
    int count = 0;
    for (Cell c : cell.getNeighbors()) {
      if (c.getCurrentState() == 1) {
        count++;
      }
    }
    numNeighbors = count;
  }

  public void setState()
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    Method celLStateChange = this.getClass()
        .getDeclaredMethod(stateAndNeighborsMap.getString(cellCurrentState + "," + numNeighbors));
    celLStateChange.setAccessible(true);
    celLStateChange.invoke(this);
  }

  //For testing purposes
  public void setNumNeighbors(int numNeighbors) {
    this.numNeighbors = numNeighbors;
  }

  public void killCell() {
    cell.setFutureState(0);
  }

  public void createCell() {cell.setFutureState(1);}

}
