package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.PredatorPreyCell;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class PredatorPreyRules extends Rules {
    private int reproductionCycle=3;

    public PredatorPreyRules(Cell cell, List<Double> args)
    {
        super(cell);
        if(args.size() > 1) {
            //TODO: actually handle
            throw new InputMismatchException();
        }
    }


    public void setState()
    {
        ifCurrentStateOne();
        ifCurrentStateTwo();
        cell.updateChronon();
    }


    public void checkReproduction()
    {
        if (cell.getChrononCounter()==reproductionCycle) {
            cell.setFutureState(cell.getCurrentState());
            cell.resetChronon();
        }
        else {cell.setFutureState(0);}
    }


    public void ifCurrentStateOne(){

        if(cell.getCurrentState()==1)
        {
            if(cell.numOfStateNeighbors(0)>0) {
                move(0);
                checkReproduction();
            }
            else{
                cell.setFutureState(cell.getCurrentState());
            }
        }

    }

    public void ifCurrentStateTwo(){
        if (cell.getCurrentState()==2)
        {
            if(cell.getEnergy()!=0) {
                if (cell.numOfStateNeighbors(1) > 0) {
                    move(1);
                    checkReproduction();
                    cell.gainEnergy();
                } else if (cell.numOfStateNeighbors(0) > 0) {
                    move(0);
                    checkReproduction();
                }
                else{cell.setFutureState(cell.getCurrentState());}
            }
            else{cell.setFutureState(0);}
            cell.loseEnergy();
        }
    }



}

