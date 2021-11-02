package cellsociety.Utilities.CSVParser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProbabilityCSVParserTest {
  private ProbabilityCSVParser probabilityCSVParser;

  @BeforeEach
  void setUp() {
    probabilityCSVParser = new ProbabilityCSVParser();
  }

  @Test
  void fileNotFoundException() {
    probabilityCSVParser.setFile(new File("blah.csv"));
    Assertions.assertThrows(FileNotFoundException.class, () -> probabilityCSVParser.getCellStates("GameOfLife"));

  }

  @Test
  void getCellStatesGameOfLifeProbabilityOne() throws FileNotFoundException {
    probabilityCSVParser.setFile(new File("./data/game_of_life/probabilityAll.csv"));
    List<Integer> cellStates = probabilityCSVParser.getCellStates("GameOfLife");
    for(Integer cellState : cellStates) {
      Assertions.assertEquals(1, cellState);
    }
  }

  @Test
  void getCellStatesGameOfLifeProbabilityZero() throws FileNotFoundException {
    probabilityCSVParser.setFile(new File("./data/game_of_life/probabilityNone.csv"));
    List<Integer> cellStates = probabilityCSVParser.getCellStates("GameOfLife");
    for(Integer cellState : cellStates) {
      Assertions.assertEquals(0, cellState);
    }
  }

  @Test
  void getCellStatesFireProbabilityTwoOne() throws FileNotFoundException {
    probabilityCSVParser.setFile(new File("./data/fire/probabilityAllTwo.csv"));
    List<Integer> cellStates = probabilityCSVParser.getCellStates("Fire");
    for(Integer cellState : cellStates) {
      Assertions.assertEquals(2, cellState);
    }
  }
}