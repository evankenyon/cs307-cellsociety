package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;
import java.util.Random;

public class SpreadingOfFireRulesInterface implements RulesInterface {
    private Cell cell;
    private int cellCurrentState;
    private List<Cell> neighbors;
    private double probCatch=.15;
    private double probGrow=.15;

    public SpreadingOfFireRulesInterface(Cell cell)
    {
        this.cell=cell;
        this.cellCurrentState=cell.getCurrentState();
        this.neighbors=cell.getNeighbors();
    }


    public void setState()
    {
        switch (cellCurrentState) {
            //empty
            case 0 -> growTree();

            //tree
            case 1 -> burnTree();

            //burning
            case 2 -> cell.setFutureState(0);
        }
    }

    public void growTree()
    {
        Random rand=new Random();
        double chance= rand.nextDouble();
        if (chance<probGrow) {cell.setFutureState(1);}
        else{cell.setFutureState(0);}
    }

    public void burnTree()
    {
        int count=0;
        Random rand=new Random();
        double chance= rand.nextDouble();
        for (Cell neighbor: neighbors)
        {
            if (neighbor.getCurrentState()==2)
            {
                count++;
            }
        }
        if (count>0 && chance<probCatch){cell.setFutureState(2);}
        else {cell.setFutureState(1);}
    }


}
