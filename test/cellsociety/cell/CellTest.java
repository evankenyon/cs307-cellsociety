package cellsociety.cell;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

class CellTest {
  private Cell cell;
  private int DEFAULT_GRID_WIDTH = 100;
  private int DEFAULT_GRID_HEIGHT = 100;
  @BeforeEach
  void setUp() {
    cell = new Cell(1, 1, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
  }

  @Test
  void updateNeighborsCorrect() {
    Cell potentialNeighborOne = new Cell(1, 2, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    Cell potentialNeighborTwo = new Cell(1, 0, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    cell.updateNeighbors(potentialNeighborOne, 1);
    cell.updateNeighbors(potentialNeighborTwo, 1);
    assertTrue(cell.getNeighbors().contains(potentialNeighborTwo));
    assertTrue(cell.getNeighbors().contains(potentialNeighborOne));
  }

  @Test
  void changeDisplayStateOn(){
    cell.setFutureState(1);
    cell.updateState();
    assertEquals(((Rectangle)cell.getMyDisplay()).getFill(), CellDisplay.ON_COLOR);
  }

  @Test
  void changeDisplayStateOff(){
    cell.setFutureState(0);
    cell.updateState();
    assertEquals(((Rectangle)cell.getMyDisplay()).getFill(), CellDisplay.OFF_COLOR);
  }


}