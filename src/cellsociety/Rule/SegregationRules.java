package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.SegregationCell;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class SegregationRules extends Rules {
    private double satisfactionThreshold=.25;
    private SegregationCell scell;

    public SegregationRules(SegregationCell cell, List<Double> args)
    {
        super(cell);
        scell=cell;
        if(args.size() > 1) {
            //TODO: actually handle
            throw new InputMismatchException();
        }
    }

    public void setState()
    {
        int countOfAgent = scell.numOfStateNeighbors(scell.getCurrentState());
        double satisfaction=1.0*countOfAgent / scell.getNumNeighbors();
        if(scell.getCurrentState() != 0 && scell.numOfStateNeighbors(0)>0)
        {

            if ((satisfaction) <= satisfactionThreshold) {moveCell();}
            else {scell.setFutureState(scell.getCurrentState());}
        }
        else {
            if ((satisfaction) <= satisfactionThreshold) {moveRandom(0);}
            else {scell.setFutureState(scell.getCurrentState());}
        }
    }

    public void moveCell()
    {
        scell.setFutureState(0);
        move(0);
    }

    public void moveRandom(int state)
    {
        scell.setFutureState(0);
        boolean temp=true;
        while(temp)
        {
            Random random=new Random();
            int stateNeighbors=scell.numOfStateNeighbors(state);
            int randInt= random.nextInt(stateNeighbors);
            if(scell.getNeighborOfState(state, randInt).getFutureState()==0)
            {
                scell.getNeighborOfState(state, randInt).setFutureState(scell.getCurrentState());
                temp=false;
            }

        }
    }

}
