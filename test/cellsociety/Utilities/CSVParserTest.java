package cellsociety.Utilities;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.cell.IllegalCellStateException;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVParserTest {
  private CSVParser csvParser;
  @BeforeEach
  void setUp() {
    csvParser = new CSVParser();
  }

  @Test
  void initializeCellMatrixExceptionGameOfLife() {
    csvParser.setFile(new File("./data/game_of_life/bad.csv"));
    Assertions.assertThrows(IllegalCellStateException.class, () -> csvParser.initializeCellMatrix("GameOfLife"));
  }

  @Test
  void initializeCellMatrixExceptionFire() {
    csvParser.setFile(new File("./data/fire/bad.csv"));
    Assertions.assertThrows(IllegalCellStateException.class, () -> csvParser.initializeCellMatrix("Fire"));
  }

  @Test
  void getCellStates() {
  }

  @Test
  void getRows() {
  }

  @Test
  void getCols() {
  }
}