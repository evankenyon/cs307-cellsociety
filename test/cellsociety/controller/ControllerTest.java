package cellsociety.controller;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.Model.Model;
import cellsociety.cell.Cell;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerTest {
  private Controller controller;
  private Model model;
  private MainView mainView;

  @BeforeEach
  void setUp() {
    controller = new Controller();
    model = new Model();
    mainView = new MainView();
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
}