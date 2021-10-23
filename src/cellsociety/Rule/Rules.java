package cellsociety.Rule;

import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

public abstract class Rules implements RulesInterface{
  protected Cell cell;
  protected int cellCurrentState;
  protected ResourceBundle stateAndNeighborsMap;

  public Rules(Cell cell) {
    this.cell=cell;
    this.cellCurrentState=cell.getCurrentState();
  }

  public void setState()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method cellStateChange = this.getClass()
        .getDeclaredMethod(stateAndNeighborsMap.getString(String.valueOf(cellCurrentState)));
    cellStateChange.setAccessible(true);
    cellStateChange.invoke(this);
  }

  public void setCellStateZero() {
    cell.setFutureState(0);
  }

  public void setCellStateOne() {
    cell.setFutureState(1);
  }

  public void setCellStateTwo() {
    cell.setFutureState(1);
  }

}
