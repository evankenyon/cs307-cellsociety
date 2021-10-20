package cellsociety.Utilities;

import cellsociety.cell.Cell;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVGenerator {

  private int numProgramsSaved;

  // TODO: handle exception properly
  public void createCSVFile(Cell[][] cellGrid) throws IOException {
    //TODO: make it so user can choose filepath

    // Setup code for CSVWriter borrowed from https://www.baeldung.com/opencsv
    CSVWriter csvWriter = new CSVWriter(
        new FileWriter("./data/game_of_life/saved/program-" + numProgramsSaved + ".csv"),
        ',', CSVWriter.NO_QUOTE_CHARACTER, '"', "\n");
    String[] cellGridSizeArray = new String[]{String.valueOf(cellGrid.length),
        String.valueOf(cellGrid[0].length)};
    csvWriter.writeNext(cellGridSizeArray);
    for (int row = 0; row < cellGrid.length; row++) {
      csvWriter.writeNext(cellGridRowToStates(cellGrid[row]));
    }
    csvWriter.close();
    numProgramsSaved++;
  }

  private String[] cellGridRowToStates(Cell[] cellGridRow) {
    List<String> stringStates = new ArrayList<>();
    for (int index = 0; index < cellGridRow.length; index++) {
      stringStates.add(String.valueOf(cellGridRow[index].getCurrentState()));
    }
    // Conversion code borrowed from https://www.geeksforgeeks.org/arraylist-toarray-method-in-java-with-examples/
    String[] stringStatesArr = new String[stringStates.size()];
    stringStatesArr = stringStates.toArray(stringStatesArr);
    return stringStatesArr;
  }
}
