package cellsociety.controller;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.Model.Model;
import cellsociety.Utilities.CSVParser.IllegalRowSizeException;
import cellsociety.Utilities.CSVParser.InvalidDimensionException;
import cellsociety.cell.Cell;
import cellsociety.cell.CellDisplay;
import cellsociety.cell.IllegalCellStateException;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.scene.paint.Paint;
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
          throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, IllegalCellStateException, InvalidDimensionException, IllegalRowSizeException {
    controller.parseFile(new File("./data/game_of_life/blinkers.sim"));
//    List<Cell> cells = model.getCellList();
    Scanner overallScanner = new Scanner("./data/game_of_life/blinkers.sim");
    overallScanner.nextLine();
    int row = 0;
    while (overallScanner.hasNextLine()) {
      Scanner lineScanner = new Scanner(overallScanner.nextLine());
      lineScanner.useDelimiter(",");
      int col = 0;
      while (lineScanner.hasNext()) {
        assertEquals(Integer.parseInt(lineScanner.next()), model.getCell(row, col).getCurrentState());
        col++;
      }
      row++;
    }
  }

  @Test
  void parseFileThrowsException() {
    assertThrows(NullPointerException.class,
        () -> controller.parseFile(new File("./data/game_of_life/nonexistent.sim")));
  }

  @Test
  void saveFile()
          throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, IllegalCellStateException, InvalidDimensionException, IllegalRowSizeException {
    controller.parseFile(new File("./data/game_of_life/blinkers.sim"));
    List<Cell> expected = new ArrayList<>();
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        expected.add(model.getCell(row, col));
      }
    }
    controller.saveFile("junit", new HashMap<>());
    controller.parseFile(new File("./data/saved/GameOfLife/program-junit.sim"));
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        assertEquals(expected.get(model.getRows() * row + col).getCurrentState(),
            model.getCell(row, col).getCurrentState());
      }
    }
  }
}