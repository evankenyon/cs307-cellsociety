package cellsociety.Rule;

import cellsociety.cell.SegregationCell;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class SegregationRules extends Rules {
    private double satisfactionThreshold=.25;
    private SegregationCell segregationCell;

    public SegregationRules(SegregationCell cell, List<Double> args)
    {
        super(cell);
        segregationCell =cell;
        if(args.size() > 1) {
            //TODO: actually handle
            throw new InputMismatchException();
        }
    }

    public void setState()
    {
        int countOfAgent = numberOfNeighborsWithSameState();
        double satisfaction=1.0*countOfAgent / segregationCell.getNumNeighbors();
        if(segregationCell.getCurrentState() != 0 && segregationCell.numOfStateNeighbors(0)>0)
        {

            if ((satisfaction) <= satisfactionThreshold) {moveCell();}
            else {
                segregationCell.setFutureState(segregationCell.getCurrentState());}
        }
        else {
            if ((satisfaction) <= satisfactionThreshold) {moveRandom(0);}
            else {
                segregationCell.setFutureState(segregationCell.getCurrentState());}
        }
    }

    public void moveCell()
    {
        segregationCell.setFutureState(0);
        move(0);
    }

    public void moveRandom(int state)
    {
        segregationCell.setFutureState(0);
        boolean temp=true;
        while(temp)
        {
            Random random=new Random();
            int stateNeighbors= segregationCell.numOfStateNeighbors(state);
            int randInt= random.nextInt(stateNeighbors);
            if(segregationCell.getNeighborOfState(state, randInt).getFutureState()==0)
            {
                segregationCell.getNeighborOfState(state, randInt).setFutureState(segregationCell.getCurrentState());
                temp=false;
            }

        }
    }

    public int numberOfNeighborsWithSameState(){
        return segregationCell.numOfStateNeighbors(segregationCell.getCurrentState());
    }


}
