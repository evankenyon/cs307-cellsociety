package util;
import cellsociety.Utilities.CSVParser.CSVParser;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.Utilities.CSVParser.DefaultCSVParser;
import cellsociety.cell.IllegalCellStateException;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;


public class CSVParserTest {
  CSVParser parser;
  private final String blinkerPath = "data/game_of_life/blinkers.csv";
  private final String gliderPath = "data/game_of_life/glider.csv";

  @BeforeEach
  void setup(){
    parser = new DefaultCSVParser();
  }


  @Test
  void testBlinkerSetup() throws FileNotFoundException, IllegalCellStateException {
    parser.setFile(new File(blinkerPath));
    try {
      parser.getCellStates("GameOfLife");
    } catch (Exception e){
      assertTrue(false);
    }
    List<Integer> allCells = parser.getCellStates("GameOfLife");
    //testing some random values
    assertEquals(allCells.get(0), 0);
    assertEquals(allCells.get(9), 1);
    assertEquals(allCells.get(94), 1);
  }

  @Test
  void testGliderSetup() throws FileNotFoundException, IllegalCellStateException {
    parser.setFile(new File(gliderPath));
    try {
      parser.getCellStates("GameOfLife");
    } catch (Exception e){
      assertTrue(false);
    }
    List<Integer> allCells = parser.getCellStates("GameOfLife");
    //testing some random values
    assertEquals(allCells.get(0), 0);
    assertEquals(allCells.get(27), 1);
    assertEquals(allCells.get(16), 1);
  }


}
