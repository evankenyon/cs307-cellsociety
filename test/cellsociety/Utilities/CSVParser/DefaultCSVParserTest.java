package cellsociety.Utilities.CSVParser;

import cellsociety.Utilities.CSVParser.DefaultCSVParser;
import cellsociety.cell.IllegalCellStateException;
import java.io.File;
import java.util.InputMismatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultCSVParserTest {
  private DefaultCSVParser defaultCSVParser;
  @BeforeEach
  void setUp() {
    defaultCSVParser = new DefaultCSVParser();
  }

  @Test
  void initializeCellMatrixExceptionGameOfLife() {
    defaultCSVParser.setFile(new File("./data/game_of_life/bad.csv"));
    Assertions.assertThrows(IllegalCellStateException.class, () -> defaultCSVParser.getCellStates("GameOfLife"));
  }

  @Test
  void initializeCellMatrixExceptionFire() {
    defaultCSVParser.setFile(new File("./data/fire/bad.csv"));
    Assertions.assertThrows(IllegalCellStateException.class, () -> defaultCSVParser.getCellStates("Fire"));
  }

  @Test
  void initializeCellMatrixExceptionNonCSV() {
    Assertions.assertThrows(InputMismatchException.class, () -> defaultCSVParser.setFile(new File("./data/fire/fire_corner.sim")));
  }

  @Test
  void initializeCellMatrixInvalidDimensionException() {
    defaultCSVParser.setFile(new File("./data/game_of_life/invalidDimension.csv"));
    Assertions.assertThrows(InvalidDimensionException.class, () -> defaultCSVParser.getCellStates("GameOfLife"));
  }

  @Test
  void initializeCellMatrixIllegalRowSize() {
    defaultCSVParser.setFile(new File("./data/game_of_life/illegalRow.csv"));
    Assertions.assertThrows(IllegalRowSizeException.class, () -> defaultCSVParser.getCellStates("GameOfLife"));
  }

}