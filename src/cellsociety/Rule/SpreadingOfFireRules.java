package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.FireCell;
import cellsociety.cell.IllegalCellStateException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class SpreadingOfFireRules extends Rules {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = "SpreadingOfFireRules";
  private static final String DEFAULT_PARAMS_FILENAME = "SpreadingOfFireDefaultParams";
  private Map<String, Double> probabilities;
  //private FireCell fcell;

  public SpreadingOfFireRules(Cell cell, List<Double> args) throws IllegalCellStateException {
    super(cell);
    if(cell.getCurrentState()>4){
      throw new IllegalCellStateException();
    }
    //fcell=cell;
    if (args.size() > 2) {
      //TODO: actually handle
      throw new InputMismatchException();
    }
    ResourceBundle defaultParams = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + DEFAULT_PARAMS_FILENAME);
    probabilities = new HashMap<>();
    for (int index = 0; index < 2; index++) {
      try {
        probabilities.put(defaultParams.getString(String.valueOf(index)), args.get(index));
      } catch (IndexOutOfBoundsException e) {
        probabilities.put(defaultParams.getString(String.valueOf(index)), Double.parseDouble(
            defaultParams.getString(defaultParams.getString(String.valueOf(index)))));
      }
    }

    stateAndNeighborsMap = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + STATE_AND_NEIGHBORS_MAP_FILENAME);
  }

  /**
   * Sets the state of the cell based on the rules of the game
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public void setState()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method cellStateChange = null;
    try {
      cellStateChange = this.getClass()
          .getDeclaredMethod(
              stateAndNeighborsMap.getString(String.valueOf(cell.getCurrentState())));
    } catch (NoSuchMethodException e) {
      cellStateChange = this.getClass().getSuperclass()
          .getDeclaredMethod(
              stateAndNeighborsMap.getString(String.valueOf(cell.getCurrentState())));
    }

    cellStateChange.setAccessible(true);
    cellStateChange.invoke(this);
  }

  /**
   * If the random double is less than probGrow, it changes the state of the cell from 0 to 1
   * where 0 is empty and 1 is tree
   */
  private void growTree() {
    Random rand = new Random();
    if (rand.nextDouble() < probabilities.get("probGrow")) {
      cell.setFutureState(1);
    } else {
      cell.setFutureState(0);
    }
  }

  /**
   * If the random double is less than probCatch and there is a neighbor in state 2,
   * it changes the state of the cell from 1 to 2
   * where 1 is tree and 2 is on fire
   */
  private void burnTree() {
    Random rand = new Random();
    if (cell.numOfStateNeighbors(2) > 0 && rand.nextDouble() < probabilities.get("probCatch")) {
      cell.setFutureState(2);
    } else {
      cell.setFutureState(1);
    }
  }
}
