package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class SpreadingOfFireRules extends Rules {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = "SpreadingOfFireRules";
  private static final double probCatch = .15;
  private static final double probGrow = .15;

  public SpreadingOfFireRules(Cell cell) {
    super(cell);
    stateAndNeighborsMap = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + STATE_AND_NEIGHBORS_MAP_FILENAME);
  }

  public void setState()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method cellStateChange = null;
    try {
      cellStateChange = this.getClass()
          .getDeclaredMethod(stateAndNeighborsMap.getString(String.valueOf(cell.getCurrentState())));
    } catch (NoSuchMethodException e) {
      cellStateChange = this.getClass().getSuperclass()
          .getDeclaredMethod(stateAndNeighborsMap.getString(String.valueOf(cell.getCurrentState())));
    }

    cellStateChange.setAccessible(true);
    cellStateChange.invoke(this);
  }

  private void growTree() {
    Random rand = new Random();
    if (rand.nextDouble() < probGrow) {
      cell.setFutureState(1);
    } else {
      cell.setFutureState(0);
    }
  }

  private void burnTree() {
    Random rand = new Random();
    if (cell.numOfStateNeighbors(2) > 0 && rand.nextDouble() < probCatch) {
      cell.setFutureState(2);
    } else {
      cell.setFutureState(1);
    }
  }
}
