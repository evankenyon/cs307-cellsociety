package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.SegregationCell;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class SegregationRules extends Rules {
    private double satisfactionThreshold=.25;
    //private SegregationCell segregationCell;

    public SegregationRules(Cell cell, List<Double> args)
    {
        super(cell);
        //segregationCell =cell;
        if(args.size() > 1) {
            //TODO: actually handle
            throw new InputMismatchException();
        }
    }

    public void setState()
    {
        int countOfAgent = numberOfNeighborsWithSameState();
        double satisfaction=1.0*countOfAgent / cell.getNumNeighbors();
        if(cell.getCurrentState() != 0 && cell.numOfStateNeighbors(0)>0)
        {

            if ((satisfaction) <= satisfactionThreshold) {moveCell();}
            else {
                cell.setFutureState(cell.getCurrentState());}
        }
        else {
            if ((satisfaction) <= satisfactionThreshold) {}//moveRandom(0);}
            else {
                cell.setFutureState(cell.getCurrentState());}
        }
    }

    public void moveCell()
    {
        cell.setFutureState(0);
        move(0);
    }

//    public void moveRandom(int state)
//    {
//        cell.setFutureState(0);
//        boolean temp=true;
//        while(temp)
//        {
//            Random random=new Random();
//            int stateNeighbors= segregationCell.numOfStateNeighbors(state);
//            int randInt= random.nextInt(stateNeighbors);
//            if(segregationCell.getNeighborOfState(state, randInt).getFutureState()==0)
//            {
//                segregationCell.getNeighborOfState(state, randInt).setFutureState(segregationCell.getCurrentState());
//                temp=false;
//            }
//
//        }
//    }

    public int numberOfNeighborsWithSameState(){
        return cell.numOfStateNeighbors(cell.getCurrentState());
    }


}
