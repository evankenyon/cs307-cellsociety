package cellsociety.Rule;

import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
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

  public void setCellState(int state){
    cell.setFutureState(state);
  }

  public void move(int state)
  {
    boolean temp=true;
    while(temp)
    {
      Random random=new Random();
      int stateNeighbors=cell.numOfStateNeighbors(state);
      int randInt= random.nextInt(stateNeighbors);
      if(cell.getNeighborOfState(state, randInt).getFutureState()==0)
      {
        cell.getNeighborOfState(state, randInt).setFutureState(cell.getCurrentState());
        temp=false;
      }

    }
  }

}
