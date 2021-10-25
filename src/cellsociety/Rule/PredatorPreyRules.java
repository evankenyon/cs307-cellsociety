package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;

public class PredatorPreyRules extends Rules {
    private List<Cell> neighbors;

    public PredatorPreyRules(Cell cell)
    {
        super(cell);
        this.neighbors=cell.getNeighbors();
    }


    public void setState()
    {

    }




}
