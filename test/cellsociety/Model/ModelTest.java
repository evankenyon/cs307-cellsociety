package cellsociety.Model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.Utilities.CSVParser.IllegalRowSizeException;
import cellsociety.Utilities.CSVParser.InvalidDimensionException;
import cellsociety.cell.Cell;
import cellsociety.cell.IllegalCellStateException;
import cellsociety.controller.Controller;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;
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
  void findNextStateForEachCell()
          throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException, IllegalCellStateException, InvalidDimensionException, IllegalRowSizeException {
    controller.parseFile(new File("./data/game_of_life/blinkers.sim"));
    model.findNextStateForEachCell();
    model.updateModel();
    controller.saveFile("0", null);
    controller.parseFile(new File("./data/saved/GameOfLife/program-0.sim"));
    model.findNextStateForEachCell();
    model.updateModel();
    List<Cell> actual = new ArrayList<>();
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        actual.add(model.getCell(row, col));
      }
    }
    controller.parseFile(new File("./data/game_of_life/blinkers-one-step.sim"));
    model.findNextStateForEachCell();
    model.updateModel();
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        assertEquals(model.getCell(row, col).getCurrentState(),
            actual.get(row + col).getCurrentState());
      }
    }
  }

  @Test
  void updateCells() {
  }
}