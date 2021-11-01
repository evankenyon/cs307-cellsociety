package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.SegregationCell;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class SegregationRules extends Rules {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String DEFAULT_PARAMS_FILENAME = "SegregationDefaultParams";
  private double satisfactionThreshold;
  //private SegregationCell segregationCell;

  public SegregationRules(Cell cell, List<Double> args) {
    super(cell);
    //segregationCell =cell;
    if (args.size() > 1) {
      //TODO: actually handle
      throw new InputMismatchException();
    }
    ResourceBundle defaultParams = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + DEFAULT_PARAMS_FILENAME);
    try {
      satisfactionThreshold = args.get(0);
    } catch (IndexOutOfBoundsException e) {
      satisfactionThreshold = Double.parseDouble(defaultParams.getString("satisfactionThreshold"));
    }
  }

  public void setState() {
    int countOfAgent = numberOfNeighborsWithSameState();
    double satisfaction = 1.0 * countOfAgent / cell.getNumNeighbors();
    if (cell.getCurrentState() != 0 && cell.numOfStateNeighbors(0) > 0) {

      if ((satisfaction) <= satisfactionThreshold) {
        moveCell();
      } else {
        cell.setFutureState(cell.getCurrentState());
      }
    } else {
      if ((satisfaction) <= satisfactionThreshold) {
      }//moveRandom(0);}
      else {
        cell.setFutureState(cell.getCurrentState());
      }
    }
  }

  public void moveCell() {
    cell.setFutureState(0);
    move(0);
  }

//    public void moveRandom(int state)
//    {
//        cell.setFutureState(0);
//        boolean temp=true;
//        while(temp)
//        {
//            Random random=new Random();
//            int stateNeighbors= cell.numOfStateNeighbors(state);
//            int randInt= random.nextInt(stateNeighbors);
//            if(cell.getGridState(state, randInt).getFutureState()==0)
//            {
//                cell.getGridState(state, randInt).setFutureState(cell.getCurrentState());
//                temp=false;
//            }
//
//        }
//    }

  public int numberOfNeighborsWithSameState() {
    return cell.numOfStateNeighbors(cell.getCurrentState());
  }


}
