package cellsociety.cell;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellTest {
  private Cell cell;
  @BeforeEach
  void setUp() {
    cell = new Cell(1, 1, 1);
  }

  @Test
  void updateNeighborsCorrect() {
    Cell potentialNeighborOne = new Cell(1, 2, 1);
    Cell potentialNeighborTwo = new Cell(1, 0, 1);
    cell.updateNeighbors(potentialNeighborOne);
    cell.updateNeighbors(potentialNeighborTwo);
    assertTrue(cell.getNeighbors().contains(potentialNeighborTwo));
    assertTrue(cell.getNeighbors().contains(potentialNeighborOne));
  }
}