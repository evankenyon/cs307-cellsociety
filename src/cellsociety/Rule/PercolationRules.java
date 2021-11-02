package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.IllegalCellStateException;
import cellsociety.cell.PercolationCell;

import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;

public class PercolationRules extends Rules {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = "PercolationRules";
  //private PercolationCell pcell;

  public PercolationRules(Cell cell, List<Double> args) throws IllegalCellStateException {
    super(cell);
    if(cell.getCurrentState()>2){
      throw new IllegalCellStateException();
    }

    if(cell.getCurrentState()>4){
      throw new IllegalCellStateException();
    }

    //pcell=cell;
    if (!args.isEmpty()) {
      //TODO: actually handle
      throw new InputMismatchException();
    }
    stateAndNeighborsMap = ResourceBundle.getBundle(
        DEFAULT_RESOURCE_PACKAGE + STATE_AND_NEIGHBORS_MAP_FILENAME);
  }
}
