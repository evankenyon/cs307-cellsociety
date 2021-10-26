package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;

public class SegregationRules extends Rules {
    private int cellCurrentState;
    private List<Cell> neighbors;
    private double satisfactionThreshold=.15;

    public SegregationRules(Cell cell)
    {
        super(cell);
        this.neighbors=cell.getNeighbors();
    }


    public void setState()
    {
        int countOfAgent=cell.getNeighborCellStateMap().get(cell.getCurrentState()).size();
        if ((countOfAgent/ neighbors.size())<satisfactionThreshold){moveCell();}
    }

    public void moveCell()
    {
        cell.setFutureState(0);
        cell.setShouldMove(true);
        cell.setCompareState(0);
    }

}
