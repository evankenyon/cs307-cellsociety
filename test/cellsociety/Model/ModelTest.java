package cellsociety.Model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.cell.Cell;
import cellsociety.controller.Controller;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelTest {
  private Controller controller;
  private Model model;

  @BeforeEach
  void setUp() {
    model = new Model();
    controller = new Controller();
    controller.setModel(model);
  }

  @Test
  void findNextStateForEachCell() throws IOException {
    controller.parseFile(new File("./data/game_of_life/blinkers.csv"));
    model.findNextStateForEachCell();
    model.updateModel();
    controller.saveFile("program-0");
    controller.parseFile(new File("./data/game_of_life/saved/program-0.csv"));
    model.findNextStateForEachCell();
    model.updateModel();
    Cell[][] actual = model.getCellGrid();
    controller.parseFile(new File("./data/game_of_life/blinkers-one-step.csv"));
    model.findNextStateForEachCell();
    model.updateModel();
    Cell[][] expected = model.getCellGrid();
    for (int row = 0; row < expected.length; row++) {
      for (int col = 0; col < expected[0].length; col++) {
        assertEquals(expected[row][col].getCurrentState(), actual[row][col].getCurrentState());
      }
    }
  }

  @Test
  void updateCells() {
  }
}