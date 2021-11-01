package cellsociety.Utilities.CSVParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ProbabilityCSVParser extends CSVParser {
  private double stateOneProbability;
  private double stateTwoProbability;

  @Override
  public List<Integer> getCellStates(String simType) throws FileNotFoundException {
    Scanner scanner;
    scanner = new Scanner(getFile());
    initializeProbabilities(simType, scanner);
    scanner.close();
    return initializeCellStates();
  }

  private List<Integer> initializeCellStates() {
    List<Integer> cellStates = new ArrayList<>();
    Random rand = new Random();
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getCols(); col++) {
        double nextRand = rand.nextDouble();
        if (stateOneProbability + stateTwoProbability < nextRand) {
          cellStates.add(0);
        } else if (stateOneProbability > nextRand) {
          cellStates.add(1);
        } else {
          cellStates.add(2);
        }
      }
    }
    return cellStates;
  }

  private void initializeProbabilities(String simType, Scanner scanner) {
    stateOneProbability = 0;
    stateTwoProbability = 0;

    if (scanner.hasNextLine()) {
      String dimensionInformation = scanner.nextLine();
      dimensionInformation = dimensionInformation.strip();
      String[] dimensions = dimensionInformation.split(",");
      //FIXME: Need to throw an exception when it isnt an integer
      setRows(Integer.parseInt(dimensions[1]));
      setCols(Integer.parseInt(dimensions[0]));
      stateOneProbability = Double.parseDouble(dimensions[2]);
      try {
        stateTwoProbability = Double.parseDouble(dimensions[3]);
      } catch (IndexOutOfBoundsException e) {
        if(getValidStatesList(simType).contains(2)) {
          throw new InputMismatchException();
        }
      }
    }
  }

}
