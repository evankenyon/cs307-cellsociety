package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.GameOfLifeCell;
import cellsociety.cell.IllegalCellStateException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;

public class GameOfLifeRules extends Rules {
  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = "GameOfLifeRules";
  private int numOneNeighbors;
  private GameOfLifeCell gcell;

  public GameOfLifeRules(Cell cell, List<Double> args) throws IllegalCellStateException {
    super(cell);
    if(cell.getCurrentState()>1){
      throw new IllegalCellStateException();
    }
//    gcell=cell;
    if(!args.isEmpty()) {
      // TODO: handle
      throw new InputMismatchException();
    }
    numOneNeighbors = 0;
    stateAndNeighborsMap = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + STATE_AND_NEIGHBORS_MAP_FILENAME);
  }

  //For testing purposes
  public void setNumOneNeighbors(int numOneNeighbors) {
    this.numOneNeighbors = numOneNeighbors;
  }

}
