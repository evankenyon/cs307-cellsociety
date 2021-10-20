package cellsociety.controller;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.Model.Model;
import cellsociety.cell.Cell;
import cellsociety.cell.CellDisplay;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

class ControllerTest {

  private Controller controller;
  private Model model;

  @BeforeEach
  void setUp() {
    controller = new Controller();
    model = new Model();
    MainView mainView = new MainView();
    controller.setModel(model);
    controller.setMainView(mainView);
  }

  @Test
  void parseFile() throws FileNotFoundException {
    controller.parseFile(new File("./data/game_of_life/blinkers.csv"));
    Cell[][] cells = model.getCellGrid();
    Scanner overallScanner = new Scanner("./data/game_of_life/blinkers.csv");
    overallScanner.nextLine();
    int row = 0;
    while (overallScanner.hasNextLine()) {
      Scanner lineScanner = new Scanner(overallScanner.nextLine());
      lineScanner.useDelimiter(",");
      int col = 0;
      while (lineScanner.hasNext()) {
        assertEquals(Integer.parseInt(lineScanner.next()), cells[row][col].getCurrentState());
        col++;
      }
      row++;
    }
  }

  @Test
  void parseFileThrowsException() {
    assertThrows(FileNotFoundException.class,
        () -> controller.parseFile(new File("./data/game_of_life/nonexistent.csv")));
  }

  @Test
  void saveFile() throws IOException {
    controller.parseFile(new File("./data/game_of_life/blinkers.csv"));
    Cell[][] expected = model.getCellGrid();
    controller.saveFile();
    controller.parseFile(new File("./data/game_of_life/saved/program-0.csv"));
    Cell[][] actual = model.getCellGrid();
    for (int row = 0; row < expected.length; row++) {
      for (int col = 0; col < expected[0].length; col++) {
        assertEquals(expected[row][col].getCurrentState(), actual[row][col].getCurrentState());
      }
    }
  }

  @Test
  void testStepWithSimpleCSV() throws IOException{
    controller.parseFile(new File("data/game_of_life/HandMadeTest1.csv"));
    controller.step();
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    controller.step();
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
  }

  @Test
  void testWith2x2CSV() throws IOException{
    controller.parseFile(new File("data/game_of_life/HandMadeTest2.csv"));
    controller.step();
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(1)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(2)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(3)).getFill());
    controller.step();
    //this actually fails. Not sure why
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(1)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(2)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(3)).getFill());

  }

  @Test
  void testWith3x3Border() throws IOException{
    controller.parseFile(new File("data/game_of_life/HandMadeTest3x3Border.csv"));
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(1)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(2)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(3)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(4)).getFill());
    controller.step();
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(1)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(2)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(3)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(4)).getFill());
    controller.step();
    //this actually fails. Not sure why
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(1)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(2)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(3)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(4)).getFill());

  }

  @Test
  void testWith3x3Full() throws IOException{
    controller.parseFile(new File("data/game_of_life/HandMadeTest3x3Full.csv"));
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(1)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(2)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(3)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(4)).getFill());
    controller.step();
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(1)).getFill());
    assertEquals(CellDisplay.ON_COLOR, ((Rectangle)controller.getCellDisplays().get(2)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(3)).getFill());
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(4)).getFill());
    controller.step();


  }

}