package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;
import java.util.Random;

public class PredatorPreyRules extends Rules {
    private List<Cell> neighbors;
    private int reproductionCycle=3;

    public PredatorPreyRules(Cell cell)
    {
        super(cell);
        this.neighbors=cell.getNeighbors();
    }


    public void setState()
    {
        if(cell.getCurrentState()==1)
        {
            if(cell.getNeighborStateMap().get(0)>0) {move(0);}
            else{cell.setFutureState(cell.getCurrentState());}
        }
        if (cell.getCurrentState()==2)
        {
            if(cell.getEnergy()!=0) {
                if (cell.getNeighborStateMap().get(1) > 0) {
                    move(1);
                    cell.setEnergy(cell.getEnergy() + 1);
                } else if (cell.getNeighborStateMap().get(0) > 0) {
                    move(0);
                }
                else{cell.setFutureState(cell.getCurrentState());}
            }
            else{cell.setFutureState(0);}
            cell.setEnergy(cell.getEnergy() - 1);
        }

        cell.setChrononCounter(cell.getChrononCounter()+1);
    }



    public void move(int type)
    {
        boolean temp=true;
        while(temp)
        {
            Random random=new Random();
            int randInt= random.nextInt(neighbors.size()-1);
            if(neighbors.get(randInt).getCurrentState()==type && neighbors.get(randInt).getFutureState()==type)
            {
                neighbors.get(randInt).setFutureState(cell.getCurrentState());
                temp=false;
            }
        }
        if (cell.getChrononCounter()==reproductionCycle) {
            cell.setFutureState(cell.getCurrentState());
            cell.setChrononCounter(0);
        }
        else {cell.setFutureState(0);}
    }

}
