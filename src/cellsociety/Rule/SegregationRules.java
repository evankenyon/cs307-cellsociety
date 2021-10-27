package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class SegregationRules extends Rules {
    private double satisfactionThreshold=.25;

    public SegregationRules(Cell cell, List<Double> args)
    {
        super(cell);
        if(args.size() > 1) {
            //TODO: actually handle
            throw new InputMismatchException();
        }
    }

    public void setState()
    {
        int countOfAgent = cell.numOfStateNeighbors(cell.getCurrentState());
        double satisfaction=1.0*countOfAgent / cell.getNumNeighbors();
        if(cell.getCurrentState() != 0 && cell.numOfStateNeighbors(0)>0)
        {

            if ((satisfaction) <= satisfactionThreshold) {moveCell();}
            else {cell.setFutureState(cell.getCurrentState());}
        }
        else {
            if ((satisfaction) <= satisfactionThreshold) {moveRandom(0);}
            else {cell.setFutureState(cell.getCurrentState());}
        }
    }

    public void moveCell()
    {
        cell.setFutureState(0);
        move(0);
    }

    public void moveRandom(int state)
    {
        cell.setFutureState(0);
        boolean temp=true;
        while(temp)
        {
            Random random=new Random();
            int stateNeighbors=cell.numOfStateNeighbors(state);
            int randInt= random.nextInt(stateNeighbors);
            if(cell.getNeighborOfState(state, randInt).getFutureState()==0)
            {
                cell.getNeighborOfState(state, randInt).setFutureState(cell.getCurrentState());
                temp=false;
            }

        }
    }

}
