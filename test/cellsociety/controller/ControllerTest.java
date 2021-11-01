package cellsociety.controller;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.Model.Model;
import cellsociety.cell.Cell;
import cellsociety.cell.CellDisplay;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
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
    //controller.setMainView(mainView);
  }

  @Test
  void parseFile()
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    controller.parseFile(new File("./data/game_of_life/blinkers.sim"));
    List<Cell> cells = model.getCellList();
    Scanner overallScanner = new Scanner("./data/game_of_life/blinkers.sim");
    overallScanner.nextLine();
    int row = 0;
    while (overallScanner.hasNextLine()) {
      Scanner lineScanner = new Scanner(overallScanner.nextLine());
      lineScanner.useDelimiter(",");
      int col = 0;
      while (lineScanner.hasNext()) {
        assertEquals(Integer.parseInt(lineScanner.next()), cells.get(row + col).getCurrentState());
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
  void saveFile()
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    controller.parseFile(new File("./data/game_of_life/blinkers.sim"));
    List<Cell> expected = model.getCellList();
    controller.saveFile("junit", new HashMap<>());
    controller.parseFile(new File("./data/game_of_life/saved/program-0.sim"));
    List<Cell> actual = model.getCellList();
    for (int index = 0; index < expected.size(); index++) {
        assertEquals(expected.get(index).getCurrentState(), actual.get(index).getCurrentState());
    }
  }

  @Test
  void testStepWith1x1Grid() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    controller.parseFile(new File("data/game_of_life/HandMadeTest1.sim"));
    controller.step();
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
    controller.step();
    assertEquals(CellDisplay.OFF_COLOR, ((Rectangle)controller.getCellDisplays().get(0)).getFill());
  }

  @Test
  void testWith2x2CSV() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    controller.parseFile(new File("data/game_of_life/HandMadeTest2.sim"));
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
  void testWith3x3Border() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    controller.parseFile(new File("data/game_of_life/HandMadeTest3x3Border.sim"));
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
  void testWith3x3Full() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    controller.parseFile(new File("data/game_of_life/HandMadeTest3x3Full.sim"));
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