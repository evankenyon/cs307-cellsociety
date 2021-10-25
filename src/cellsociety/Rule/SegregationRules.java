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
        int count=0;
        for (Cell neighbor: neighbors)
        {
            if (neighbor.getCurrentState()==cell.getCurrentState())
            {
                count++;
            }
        }
        if ((count/ neighbors.size())<satisfactionThreshold){}//Implement movement logic
    }

}
