package cellsociety.Rule;


import cellsociety.cell.Cell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class GameOfLifeRules extends Rules {
  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = "GameOfLifeRules";

  public GameOfLifeRules(Cell cell) {
    super(cell);
    stateAndNeighborsMap = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + STATE_AND_NEIGHBORS_MAP_FILENAME);
  }

  public void setState()
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    Method cellStateChange = this.getClass().getSuperclass()
        .getDeclaredMethod(stateAndNeighborsMap.getString(cellCurrentState + "," + numOneNeighbors));
    cellStateChange.setAccessible(true);
    cellStateChange.invoke(this);
  }

  //For testing purposes
  public void setNumOneNeighbors(int numOneNeighbors) {
    this.numOneNeighbors = numOneNeighbors;
  }

}
