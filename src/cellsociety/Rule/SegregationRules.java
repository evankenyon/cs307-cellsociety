package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.IllegalCellStateException;
import cellsociety.cell.SegregationCell;

import java.util.*;

public class SegregationRules extends Rules {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      GameOfLifeRules.class.getPackageName() + ".resources.";
  private static final String DEFAULT_PARAMS_FILENAME = "SegregationDefaultParams";
  private double satisfactionThreshold;
  //private SegregationCell segregationCell;

  public SegregationRules(Cell cell, List<Double> args) throws IllegalCellStateException {
    super(cell);
    if(cell.getCurrentState()>4){
      throw new IllegalCellStateException();
    }
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
    int countOfOtherAgent=numberOfNeighborsWithOtherState();
    double satisfaction = 1.0 * countOfAgent / (countOfOtherAgent+countOfAgent);
    if ((satisfaction) <= satisfactionThreshold && cell.getCurrentState()!=0)
    {
      if (cell.getCurrentState() != 0 && cell.numOfStateNeighbors(0) > 0) {moveCell();}
      else if (cell.getCurrentState() != 0 && cell.numOfStateNeighbors(0) == 0) {moveRandom(cell);}
      else {cell.setFutureState(cell.getCurrentState());}
    }
    else {cell.setFutureState(cell.getCurrentState());}
  }

  public void moveCell() {
    move(0);
    cell.setFutureState(0);
  }

  public void moveRandom(Cell cell)
  {
      findOpenCell(cell).setFutureState(cell.getCurrentState());
      cell.setFutureState(0);
  }

  public Cell findOpenCell(Cell c)
  {
    List<Cell> cellNeighbors=c.getNeighbors();
    List<Cell> neighborsNeighboringCells=new ArrayList<>();
    for (Cell cell:cellNeighbors)
    {
      neighborsNeighboringCells.addAll(cell.getNeighbors());
    }
    for (Cell cell2: neighborsNeighboringCells)
    {
      if (cell2.getFutureState()==0 && cell2.getCurrentState()==0) {return cell2;}
    }
    return findOpenCell(neighborsNeighboringCells.get(chooseRandomIndex(neighborsNeighboringCells.size())));
  }

  public int chooseRandomIndex(int size)
  {
    Random random=new Random();
    int randInt= random.nextInt(size);
    return randInt;
  }

  public int numberOfNeighborsWithSameState() {
    return cell.numOfStateNeighbors(cell.getCurrentState());
  }


  public int numberOfNeighborsWithOtherState() {
    if(cell.getCurrentState()==1) {return cell.numOfStateNeighbors(2);}
    return cell.numOfStateNeighbors(1);
  }

}
