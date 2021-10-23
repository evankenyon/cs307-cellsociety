package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;

public class PredatorPreyRulesInterface implements RulesInterface {
    private Cell cell;
    private int cellCurrentState;
    private List<Cell> neighbors;


    public PredatorPreyRulesInterface(Cell cell)
    {
        this.cell=cell;
        this.cellCurrentState=cell.getCurrentState();
        this.neighbors=cell.getNeighbors();
    }


    public void setState()
    {

    }




}
