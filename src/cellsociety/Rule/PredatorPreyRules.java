package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.util.List;
import java.util.Random;

public class PredatorPreyRules extends Rules {
    private int reproductionCycle=3;

    public PredatorPreyRules(Cell cell)
    {
        super(cell);
    }


    public void setState()
    {
        ifCurrentStateOne();
        ifCurrentStateTwo();
        incrementCellChrononCounter();
    }


    public void checkReproduction()
    {
        if (cell.getChrononCounter()==reproductionCycle) {
            cell.setFutureState(cell.getCurrentState());
            cell.setChrononCounter(0);
        }
        else {cell.setFutureState(0);}
    }


    public void ifCurrentStateOne(){

        if(cell.getCurrentState()==1)
        {
            if(cell.getNeighborCellStateMap().get(0).size()>0) {
                cell.setCompareState(0);
                cell.setShouldMove(true);
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
                if (cell.getNeighborCellStateMap().get(1).size() > 0) {
                    cell.setCompareState(1);
                    cell.setShouldMove(true);
                    checkReproduction();
                    cell.setEnergy(cell.getEnergy() + 1);
                } else if (cell.getNeighborCellStateMap().get(0).size() > 0) {
                    cell.setCompareState(0);
                    cell.setShouldMove(true);
                    checkReproduction();
                }
                else{cell.setFutureState(cell.getCurrentState());}
            }
            else{cell.setFutureState(0);}
            cell.setEnergy(cell.getEnergy() - 1);
        }
    }
    public void incrementCellChrononCounter(){
        cell.setChrononCounter(cell.getChrononCounter()+1);
    }
}
