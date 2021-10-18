package cellsociety.cell;

import cellsociety.location.CornerLocation;

import java.util.*;

public class Cell {

    private int currentState;
    private int futureState;
    List<Cell> neighbors;
    Cell[][] cellGrid;
    List<CornerLocation> corners;
    private int iIndex;
    private int jIndex;

    public Cell(int i, int j, int intialState){
        this.iIndex = i;
        this.jIndex = j;
        this.currentState = intialState;
    }



    public void updateState(){
        currentState = futureState;
    }


    public void setFutureState(int state){
        this.futureState = futureState;
    }

    //FIXME: Not complete
    public List<Cell> getNeighbors(){

        ArrayList<Cell> neighborList = new ArrayList<>();

        //FIXME: loop through the cellGrid somehow using corners, then do this next part:
        Cell cell = new Cell(0,0,0);
        if (!neighborList.contains(cell)){
            neighborList.add(cell);
        }

        return neighborList;
    }


    private void setCellGrid(Cell[][] cellGrid){
        this.cellGrid = cellGrid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return currentState == cell.currentState && futureState == cell.futureState && iIndex == cell.iIndex && jIndex == cell.jIndex && getNeighbors().equals(cell.getNeighbors()) && Arrays.equals(cellGrid, cell.cellGrid) && corners.equals(cell.corners);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentState, futureState, getNeighbors(), corners, iIndex, jIndex);
        result = 31 * result + Arrays.hashCode(cellGrid);
        return result;
    }
}
