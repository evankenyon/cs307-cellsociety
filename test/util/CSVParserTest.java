package util;
import cellsociety.Rule.GameOfLifeRules;
import cellsociety.cell.Cell;
import cellsociety.Utilities.CSVParser;
import org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.io.File;


public class CSVParserTest {
  CSVParser parser;
  private final String blinkerPath = "data/game_of_life/blinkers.csv";
  private final String gliderPath = "data/game_of_life/glider.csv";

  @BeforeEach
  void setup(){
    parser = new CSVParser();
  }


  @Test
  void testBlinkerSetup(){
    parser.setFile(new File(blinkerPath));
    try {
      parser.initializeCellMatrix();
    } catch (Exception e){
      assertTrue(false);
    }
    Cell[][] gridMatrix = parser.getCellMatrix();
    //testing some random values
    assertEquals(gridMatrix[0][0].getCurrentState(), 0);
    assertEquals(gridMatrix[0][9].getCurrentState(), 1);
    assertEquals(gridMatrix[9][4].getCurrentState(), 1);
  }

  @Test
  void testGliderSetup(){
    parser.setFile(new File(gliderPath));
    try {
      parser.initializeCellMatrix();
    } catch (Exception e){
      assertTrue(false);
    }
    Cell[][] gridMatrix = parser.getCellMatrix();
    //testing some random values
    assertEquals(gridMatrix[0][0].getCurrentState(), 0);
    assertEquals(gridMatrix[2][1].getCurrentState(), 1);
    assertEquals(gridMatrix[1][3].getCurrentState(), 1);
  }


}
