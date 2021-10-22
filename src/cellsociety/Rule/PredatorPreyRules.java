package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;
import java.util.Random;

public class PredatorPreyRules implements Rules{
    private Cell cell;
    private int cellCurrentState;
    private List<Cell> neighbors;


    public PredatorPreyRules(Cell cell)
    {
        this.cell=cell;
        this.cellCurrentState=cell.getCurrentState();
        this.neighbors=cell.getNeighbors();
    }


    public void setState()
    {

    }




}
