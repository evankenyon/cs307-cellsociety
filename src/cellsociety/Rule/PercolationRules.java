package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;

public class PercolationRules implements Rules{

    private Cell cell;
    private int cellCurrentState;
    private List<Cell> neighbors;

    public PercolationRules(Cell cell)
    {
        this.cell=cell;
        this.cellCurrentState=cell.getCurrentState();
        this.neighbors=cell.getNeighbors();
    }


    public void setState()
    {
        switch(cellCurrentState)
        {
            case 0:
                cell.setFutureState(0);
            case 1:
                percolate();
            case 2:
                cell.setFutureState(2);
        }
    }

    public void percolate()
    {
        int count=0;
        for (Cell neighbor: neighbors)
        {
            if (neighbor.getCurrentState()==1)
            {
                count++;
            }
        }
        if (count==0){cell.setFutureState(1);}
        else {cell.setFutureState(2);}
    }


}
