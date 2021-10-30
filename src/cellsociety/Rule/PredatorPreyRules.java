package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.PredatorPreyCell;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class PredatorPreyRules extends Rules {
    private int reproductionCycle=3;
    PredatorPreyCell ppcell;
    public PredatorPreyRules(PredatorPreyCell cell, List<Double> args)
    {
        super(cell);
        ppcell=cell;
        if(args.size() > 1) {
            //TODO: actually handle
            throw new InputMismatchException();
        }
    }


    public void setState()
    {
        ifCurrentStateOne();
        ifCurrentStateTwo();
        ppcell.updateChronon();
    }


    public void checkReproduction()
    {
        if (ppcell.getChrononCounter()==reproductionCycle) {
            ppcell.setFutureState(ppcell.getCurrentState());
            ppcell.resetChronon();
        }
        else {ppcell.setFutureState(0);}
    }


    public void ifCurrentStateOne(){

        if(ppcell.getCurrentState()==1)
        {
            if(ppcell.numOfStateNeighbors(0)>0) {
                move(0);
                checkReproduction();
            }
            else{
                ppcell.setFutureState(ppcell.getCurrentState());
            }
        }

    }

    public void ifCurrentStateTwo(){
        if (cell.getCurrentState()==2)
        {
            if(ppcell.getEnergy()!=0) {
                if (ppcell.numOfStateNeighbors(1) > 0) {
                    move(1);
                    checkReproduction();
                    ppcell.gainEnergy();
                } else if (ppcell.numOfStateNeighbors(0) > 0) {
                    move(0);
                    checkReproduction();
                }
                else{ppcell.setFutureState(ppcell.getCurrentState());}
            }
            else{ppcell.setFutureState(0);}
            ppcell.loseEnergy();
        }
    }



}

