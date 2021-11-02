package cellsociety.cell;

public class FireCell extends Cell {

  public FireCell(int i, int j, int initialState, int rows, int cols)
      throws IllegalCellStateException {
    super(i, j, initialState, rows, cols, "Rectangle");
    if (initialState > 3 || initialState < 0) {
      throw new IllegalCellStateException();
    }
  }

}

