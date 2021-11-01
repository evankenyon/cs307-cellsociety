package cellsociety.Utilities.CSVParser;

import cellsociety.cell.IllegalCellStateException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DefaultCSVParser extends CSVParser {

  @Override
  public List<Integer> getCellStates(String simType) throws FileNotFoundException, IllegalCellStateException {
    Scanner scanner;
    scanner = new Scanner(getFile());

    int[][] integer2DArray = initializeInteger2DArray(scanner);
    return integer2DArrayToCellList(integer2DArray, simType);
  }

  private int[][] initializeInteger2DArray(Scanner scanner) {
    if (scanner.hasNextLine()) {
      String dimensionInformation = scanner.nextLine();
      dimensionInformation = dimensionInformation.strip();
      String[] dimensions = dimensionInformation.split(",");
      //FIXME: Need to throw an exception when it isnt an integer
      setRows(Integer.parseInt(dimensions[1]));
      setCols(Integer.parseInt(dimensions[0]));
    }
    int[][] integer2DArray = new int[getRows()][getCols()];
    int currentXIndex = 0;
    while (scanner.hasNextLine()) {
      String xIndexInfo = scanner.nextLine();
      xIndexInfo = xIndexInfo.strip();
      integer2DArray[currentXIndex] = Arrays.stream(xIndexInfo.split(","))
          .mapToInt(Integer::parseInt).toArray();
      currentXIndex++;
    }

    scanner.close();
    return integer2DArray;
  }

  private List<Integer> integer2DArrayToCellList(int[][] integer2DArray, String simType)
      throws IllegalCellStateException {
    List<Integer> cellStates = new ArrayList<>();
    List<Integer> validStatesList = getValidStatesList(simType);
    for(int row = 0; row < integer2DArray.length; row++){
      for(int col = 0; col < integer2DArray[0].length; col++){
        if (!validStatesList.contains(integer2DArray[row][col])) {
          throw new IllegalCellStateException();
        }
        cellStates.add(integer2DArray[row][col]);
      }
    }
    return cellStates;
  }

}
