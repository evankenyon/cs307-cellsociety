package cellsociety.cell;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    cell.updateCellNeighborStateMap();
    assertEquals(2, cell.numOfStateNeighbors(1));
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

  @Test
  void createNeighborStateMapCorrect() {
    int expected = 1;
    Cell potentialNeighborOne = new Cell(1, 2, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    Cell potentialNeighborTwo = new Cell(1, 0, 0, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    cell.updateNeighbors(potentialNeighborOne, 1);
    cell.updateNeighbors(potentialNeighborTwo, 1);
    cell.updateCellNeighborStateMap();
    assertEquals(expected, cell.numOfStateNeighbors(1));
    assertEquals(expected, cell.numOfStateNeighbors(0));
  }


}