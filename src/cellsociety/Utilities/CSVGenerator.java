package cellsociety.Utilities;

import cellsociety.Model.Model;
import cellsociety.cell.Cell;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVGenerator {

  public void createCSVFile(Model model, String filePath, String simType) throws IOException {
    String realFilePath =  String.format("./data/saved/%s/program-" + "%s.csv", simType, filePath);
    // Setup code for CSVWriter borrowed from https://www.baeldung.com/opencsv
    CSVWriter csvWriter = new CSVWriter(
        new FileWriter(realFilePath),
        ',', CSVWriter.NO_QUOTE_CHARACTER, '"', "\n");
    String[] cellGridSizeArray = new String[]{String.valueOf(model.getRows()),
        String.valueOf(model.getCols())};
    csvWriter.writeNext(cellGridSizeArray);
    for (int row = 0; row < model.getRows(); row++) {
      List<Cell> cellRow = new ArrayList<>();
      for (int col = 0; col < model.getCols(); col++) {
        cellRow.add(model.getCell(row, col));
      }
      csvWriter.writeNext(cellGridRowToStates(cellRow));
    }
    csvWriter.close();
  }

  private String[] cellGridRowToStates(List<Cell> cellGridRow) {
    List<String> stringStates = new ArrayList<>();
    for (int index = 0; index < cellGridRow.size(); index++) {
      stringStates.add(String.valueOf(cellGridRow.get(index).getCurrentState()));
    }
    // Conversion code borrowed from https://www.geeksforgeeks.org/arraylist-toarray-method-in-java-with-examples/
    String[] stringStatesArr = new String[stringStates.size()];
    stringStatesArr = stringStates.toArray(stringStatesArr);
    return stringStatesArr;
  }
}
