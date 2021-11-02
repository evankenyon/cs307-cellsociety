package cellsociety.Utilities.CSVParser;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.cell.IllegalCellStateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RawNumsCSVParserTest {
  private RawNumsCSVParser rawNumsCSVParser;

  @BeforeEach
  void setUp() {
    rawNumsCSVParser = new RawNumsCSVParser();
  }

  @Test
  void getCellsStateGameOfLife2and2() throws FileNotFoundException, IllegalCellStateException {
    rawNumsCSVParser.setFile(new File("./data/game_of_life/rawNumsHalfAndHalf.csv"));
    List<Integer> cellStates = rawNumsCSVParser.getCellStates("GameOfLife");
    int oneCount = 0;
    int zeroCount = 0;
    for (Integer cellState : cellStates) {
      if(cellState == 1) {
        oneCount++;
      } else {
        zeroCount++;
      }
    }
    assertEquals(2, oneCount);
    assertEquals(2, zeroCount);
  }

  @Test
  void getCellsStateFire2and2() throws FileNotFoundException, IllegalCellStateException {
    rawNumsCSVParser.setFile(new File("./data/fire/rawNumsHalfAndHalf.csv"));
    List<Integer> cellStates = rawNumsCSVParser.getCellStates("Fire");
    int oneCount = 0;
    int twoCount = 0;
    for (Integer cellState : cellStates) {
      if(cellState == 1) {
        oneCount++;
      } else if (cellState == 2) {
        twoCount++;
      }
    }
    assertEquals(2, oneCount);
    assertEquals(2, twoCount);
  }

}