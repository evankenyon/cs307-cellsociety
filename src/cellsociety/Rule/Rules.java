package cellsociety.Rule;

import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

public abstract class Rules implements RulesInterface {
  protected Cell cell;
  protected int cellCurrentState;
  protected ResourceBundle stateAndNeighborsMap;
  protected int numOneNeighbors;

  public Rules(Cell cell) {
    this.cell=cell;
    this.cellCurrentState=cell.getCurrentState();
    generateNumOneNeighbors();
  }

  public void setState()
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    Method cellStateChange = this.getClass()
        .getDeclaredMethod(stateAndNeighborsMap.getString(cellCurrentState + "," + numOneNeighbors));
    cellStateChange.setAccessible(true);
    cellStateChange.invoke(this);
  }

  private void generateNumOneNeighbors() {
    int count = 0;
    for (Cell c : cell.getNeighbors()) {
      if (c.getCurrentState() == 1) {
        count++;
      }
    }
    numOneNeighbors = count;
  }


  public void setCellStateZero() {
    cell.setFutureState(0);
  }

  public void setCellStateOne() {
    cell.setFutureState(1);
  }

  public void setCellStateTwo() {
    cell.setFutureState(2);
  }

}
