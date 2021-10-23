package cellsociety.Rule;


import cellsociety.cell.Cell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class GameOfLifeRules extends Rules {
  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = "GameOfLifeRules";
  private int numNeighbors;

  public GameOfLifeRules(Cell cell) {
    super(cell);
    generateNumNeighbors();
    stateAndNeighborsMap = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + STATE_AND_NEIGHBORS_MAP_FILENAME);
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
    Method cellStateChange = this.getClass()
        .getDeclaredMethod(stateAndNeighborsMap.getString(cellCurrentState + "," + numNeighbors));
    cellStateChange.setAccessible(true);
    cellStateChange.invoke(this);
  }

  //For testing purposes
  public void setNumNeighbors(int numNeighbors) {
    this.numNeighbors = numNeighbors;
  }

}
