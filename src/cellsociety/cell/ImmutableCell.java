package cellsociety.cell;

import cellsociety.location.CornerLocation;
import java.util.List;

public interface ImmutableCell {

  public int getjIndex();
  public int getiIndex();
  public int getCurrentState();
  public int getFutureState();
  public List<CornerLocation> getCorners();
  public int iIndexDifference(Cell potentialNeighbor);
  public int jIndexDifference(Cell potentialNeighbor);
  public int numOfStateNeighbors(int state);
  public List<Cell> getNeighbors();
  public Cell getNeighborOfState(int state, int num);
  public int getChrononCounter();
  public int getEnergy();
}
