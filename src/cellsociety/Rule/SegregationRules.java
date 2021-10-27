package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;
import java.util.Random;

public class SegregationRules extends Rules {
    private double satisfactionThreshold=.25;

    public SegregationRules(Cell cell)
    {
        super(cell);
    }

    public void setState()
    {
        if(cell.getCurrentState() != 0 && cell.numOfStateNeighbors(0)>0)
        {
            int countOfAgent = cell.numOfStateNeighbors(cell.getCurrentState());
            double satisfaction=1.0*countOfAgent / cell.getNumNeighbors();
            if ((satisfaction) <= satisfactionThreshold) {moveCell();}
            else {cell.setFutureState(cell.getCurrentState());}
        }
        else {cell.setFutureState(cell.getCurrentState());}
    }

    public void moveCell()
    {
        cell.setFutureState(0);
        move(0);
    }

}
