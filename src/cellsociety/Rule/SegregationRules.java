package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;
import java.util.Random;

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
        if(cell.getCurrentState() != 0) {
            int countOfAgent = cell.numOfStateNeighbors(cell.getCurrentState());
            if ((countOfAgent / neighbors.size()) < satisfactionThreshold) {moveCell();}
        }
        else {cell.setFutureState(cell.getCurrentState());}
    }

    public void moveCell()
    {
        cell.setFutureState(0);
        move(0);
    }

}
