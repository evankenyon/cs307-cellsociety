package cellsociety.Model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.controller.Controller;
import java.io.File;
import java.io.FileNotFoundException;
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
    model.updateCells();
    controller.saveFile();
  }

  @Test
  void updateCells() {
  }
}