package cellsociety.Rule;


import cellsociety.cell.Cell;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameOfLifeRules implements Rules{
    private Cell cell;
    private int cellCurrentState;
    private int numNeighbors;

    public GameOfLifeRules(Cell cell)
    {
        this.cell=cell;
        generateNumNeighbors(cell.getNeighbors());
        cellCurrentState=cell.getCurrentState();
    }

    public void generateNumNeighbors(List<Cell> neighbors)
    {
        int count = 0;
        for (Cell c: neighbors)
        {
            if(c.getCurrentState()==1)
            {
                count++;
            }
        }
        numNeighbors=count;
    }

    public void setState()
    {
        switch(cellCurrentState)
        {
            case 0:
                deadCell();
                break;
            case 1:
                liveCell();
        }
    }

    //For testing purposes
    public void setNumNeighbors(int numNeighbors){this.numNeighbors=numNeighbors;}

    public void killCell()
    {
        cell.setFutureState(0);
    }

    public void createCell()
    {
        cell.setFutureState(1);
    }

    public void deadCell()
    {
        if (numNeighbors==3) {createCell();}
        else {killCell();}
    }

    public void liveCell()
    {
            if (numNeighbors==3 || numNeighbors==2) {createCell();}
            else {killCell();}
    }



}
