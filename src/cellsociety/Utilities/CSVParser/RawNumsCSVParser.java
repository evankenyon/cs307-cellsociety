package cellsociety.Utilities.CSVParser;

import cellsociety.cell.IllegalCellStateException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RawNumsCSVParser extends CSVParser {

  private List<Integer> numStates;

  public RawNumsCSVParser() {
    numStates = new ArrayList<>();
  }

  @Override
  public List<Integer> getCellStates(String simType)
      throws FileNotFoundException, IllegalCellStateException {
    Scanner scanner;
    scanner = new Scanner(getFile());
    initializeRawNums(simType, scanner);
    scanner.close();
    return initializeCellStates();
  }

  private void initializeRawNums(String simType, Scanner scanner) {
    if (scanner.hasNextLine()) {
      String dimensionInformation = scanner.nextLine();
      dimensionInformation = dimensionInformation.strip();
      String[] dimensions = dimensionInformation.split(",");
      //FIXME: Need to throw an exception when it isnt an integer
      setRows(Integer.parseInt(dimensions[1]));
      setCols(Integer.parseInt(dimensions[0]));
      numStates.add(0);
      numStates.add(Integer.parseInt(dimensions[2]));
      try {
        numStates.add(Integer.parseInt(dimensions[3]));
      } catch (IndexOutOfBoundsException e) {
        if (getValidStatesList(simType).contains(2)) {
          throw new InputMismatchException();
        }
        numStates.add(0);
      }
    }
  }

  private List<Integer> initializeCellStates() {

    int currState = 0;
    List<Integer> cellStates = new ArrayList<>();
    numStates.set(0, getRows() * getCols() - numStates.get(1) - numStates.get(2));
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getCols(); col++) {
        while (numStates.get(currState) <= 0) {
          currState = (currState + 1) % 3;
        }
        cellStates.add(currState);
        numStates.set(currState, numStates.get(currState) - 1);
        currState = (currState + 1) % 3;
      }
    }
    return cellStates;
  }
}
