package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.PredatorPreyCell;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;

public class PredatorPreyRules extends Rules {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String DEFAULT_PARAMS_FILENAME = "PredatorPreyDefaultParams";
  private int reproductionCycle;

  public PredatorPreyRules(Cell cell, List<Double> args) throws InputMismatchException {
    super(cell);
    if (args.size() > 1) {
      throw new InputMismatchException();
    }
    ResourceBundle defaultParams = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + DEFAULT_PARAMS_FILENAME);
    try {
      reproductionCycle = args.get(0).intValue();
    } catch (IndexOutOfBoundsException e) {
      reproductionCycle = Integer.parseInt(defaultParams.getString("reproductionCycle"));
    }
  }


  public void setState() {
    ifCurrentStateOne();
    ifCurrentStateTwo();
    cell.updateChronon();
  }


  public void checkReproduction() {
    if (cell.getChrononCounter() == reproductionCycle) {
      cell.setFutureState(cell.getCurrentState());
      cell.resetChronon();
    } else {
      cell.setFutureState(0);
    }
  }


  public void ifCurrentStateOne() {

    if (cell.getCurrentState() == 1) {
      if (cell.numOfStateNeighbors(0) > 0) {
        move(0);
        checkReproduction();
      } else {
        cell.setFutureState(cell.getCurrentState());
      }
    }

  }

  public void ifCurrentStateTwo() {
    if (cell.getCurrentState() == 2) {
      if (cell.getEnergy() != 0) {
        if (cell.numOfStateNeighbors(1) > 0) {
          move(1);
          checkReproduction();
          cell.gainEnergy();
        } else if (cell.numOfStateNeighbors(0) > 0) {
          move(0);
          checkReproduction();
        } else {
          cell.setFutureState(cell.getCurrentState());
        }
      } else {
        cell.setFutureState(0);
      }
      cell.loseEnergy();
    }
  }


}

