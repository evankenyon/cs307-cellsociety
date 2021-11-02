package cellsociety.cell;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.shape.Rectangle;

class CellTest {
  private Cell cell;
  private int DEFAULT_GRID_WIDTH = 100;
  private int DEFAULT_GRID_HEIGHT = 100;
  private List<Integer> defaultNumCornersShared;

  @BeforeEach
  void setUp() {
    defaultNumCornersShared = new ArrayList<>();
    defaultNumCornersShared.add(1);
    defaultNumCornersShared.add(2);
    cell = new Cell(1, 1, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
  }

  @Test
  void constructorThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new Cell(2, 2, 1, 1, 1));
  }

  @Test
  void updateNeighborsAnyCornersCorrect() {
    Cell potentialNeighborOne = new Cell(1, 2, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    Cell potentialNeighborTwo = new Cell(1, 0, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    cell.updateNeighbors(potentialNeighborOne, defaultNumCornersShared);
    cell.updateNeighbors(potentialNeighborTwo, defaultNumCornersShared);
    cell.updateCellNeighborStateMap();
    assertEquals(2, cell.numOfStateNeighbors(1));
  }

  @Test
  void updateNeighbors1CornerCorrect() {
    Cell potentialNeighborOne = new Cell(0, 1, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    Cell potentialNeighborTwo = new Cell(0, 0, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    Cell potentialNeighborThree = new Cell(2, 2, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    List<Integer> numCornersShared = new ArrayList<>();
    numCornersShared.add(1);
    cell.updateNeighbors(potentialNeighborOne, numCornersShared);
    cell.updateNeighbors(potentialNeighborTwo, numCornersShared);
    cell.updateNeighbors(potentialNeighborThree, numCornersShared);
    cell.updateCellNeighborStateMap();
    assertEquals(2, cell.numOfStateNeighbors(1));
  }

  @Test
  void updateNeighbors2CornerCorrect() {
    Cell potentialNeighborOne = new Cell(0, 1, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    Cell potentialNeighborTwo = new Cell(0, 0, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    Cell potentialNeighborThree = new Cell(2, 2, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    List<Integer> numCornersShared = new ArrayList<>();
    numCornersShared.add(2);
    cell.updateNeighbors(potentialNeighborOne, numCornersShared);
    cell.updateNeighbors(potentialNeighborTwo, numCornersShared);
    cell.updateNeighbors(potentialNeighborThree, numCornersShared);
    cell.updateCellNeighborStateMap();
    assertEquals(1, cell.numOfStateNeighbors(1));
  }

  @Test
  void createNeighborStateMapCorrect() {
    int expected = 1;
    Cell potentialNeighborOne = new Cell(1, 2, 1, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    Cell potentialNeighborTwo = new Cell(1, 0, 0, DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH);
    cell.updateNeighbors(potentialNeighborOne, defaultNumCornersShared);
    cell.updateNeighbors(potentialNeighborTwo, defaultNumCornersShared);
    cell.updateCellNeighborStateMap();
    assertEquals(expected, cell.numOfStateNeighbors(1));
    assertEquals(expected, cell.numOfStateNeighbors(0));
  }

  @Test
  void torodialTest(){
    Cell oppositeSideCell = new Cell(0,99,1,100,100);
    Cell firstSideCell = new Cell(0, 0, 1, 100, 100);
    firstSideCell.updateNeighborsToroidal(100,100, oppositeSideCell, defaultNumCornersShared);
    assertTrue(firstSideCell.getNeighbors().contains(oppositeSideCell));

  }

  @Test
  void mirrorTest(){
    Cell firstSideCell = new Cell(0, 0, 1, 8, 8);
    Cell oppositeSideCell = new Cell(0,7,1,8,8);
    firstSideCell.updateNeighborsMirror(8,8, oppositeSideCell, defaultNumCornersShared);
    assertTrue(firstSideCell.getNeighbors().contains(oppositeSideCell));

  }


}