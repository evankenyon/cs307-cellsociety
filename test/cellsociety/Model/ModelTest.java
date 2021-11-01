package cellsociety.Model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.cell.Cell;
import cellsociety.controller.Controller;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
      throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException {
    controller.parseFile(new File("./data/game_of_life/blinkers.sim"));
    model.findNextStateForEachCell();
    model.updateModel();
    controller.saveFile("0", null);
    controller.parseFile(new File("./data/saved/GameOfLife/program-0.sim"));
    model.findNextStateForEachCell();
    model.updateModel();
    List<Cell> actual = model.getCellList();
    controller.parseFile(new File("./data/game_of_life/blinkers-one-step.sim"));
    model.findNextStateForEachCell();
    model.updateModel();
    List<Cell> expected = model.getCellList();
    for (int index = 0; index < expected.size(); index++) {
      assertEquals(expected.get(index).getCurrentState(), actual.get(index).getCurrentState());
    }
  }

  @Test
  void updateCells() {
  }
}