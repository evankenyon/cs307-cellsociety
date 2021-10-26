package cellsociety.Rule;

import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

public abstract class Rules implements RulesInterface {
  protected Cell cell;
  protected ResourceBundle stateAndNeighborsMap;

  public Rules(Cell cell) {
    this.cell=cell;
  }

  public void setState()
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    Method cellStateChange = null;
    try {
      cellStateChange = this.getClass()
          .getDeclaredMethod(stateAndNeighborsMap.getString(cell.getCurrentState() + "," + cell.numOfStateNeighbors(1)));
    } catch (NoSuchMethodException e) {
      cellStateChange = this.getClass().getSuperclass()
          .getDeclaredMethod(stateAndNeighborsMap.getString(cell.getCurrentState() + "," + cell.numOfStateNeighbors(1)));
    }

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
    cell.setFutureState(2);
  }

}
