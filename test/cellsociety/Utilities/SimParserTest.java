package cellsociety.Utilities;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimParserTest {

  private SimParser simParser;

  @BeforeEach
  void setUp() {
    simParser = new SimParser();
  }

  @Test
  void setupKeyValuePairsCorrect() throws IOException {
    String[] expectedKeys = {"Type", "Title", "Author", "Description", "InitialStates"};
    String[] expectedValues = {"GameOfLife", "Blinkers", "John Conway", "Examples of a blinker, a "
        + "line of cells 3 wide that switches back and forth from vertical to horizontal",
        "game_of_life/blinkers.csv"};
    simParser.setupKeyValuePairs(new File("./data/game_of_life/blinkers.sim"));
    for (String key : simParser.getSimulationConfig().stringPropertyNames()) {
      Assertions.assertTrue(Arrays.asList(expectedKeys).contains(key));
      Assertions.assertTrue(
          Arrays.asList(expectedValues).contains(simParser.getSimulationConfig().getProperty(key)));
    }
  }

  @Test
  void setupKeyValuePairsIncorrect() {
    Assertions.assertThrows(NullPointerException.class,
        () -> simParser.setupKeyValuePairs(new File("/data/game_of_life/blinkers-two-step.sim")));
  }

  @Test
  void setupKeyValuePairsInputMismatchException() {
    Assertions.assertThrows(InputMismatchException.class,
        () -> simParser.setupKeyValuePairs(new File("/data/game_of_life")));
  }
}