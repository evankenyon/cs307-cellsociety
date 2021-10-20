package cellsociety.Rule;


import cellsociety.cell.Cell;

import java.util.List;

public class GameOfLifeRules {
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
                switch(numNeighbors)
                {
                    case 3:
                        createCell();
                        break;
                    case 0: case 1: case 2: case 4: case 5: case 6: case 7: case 8:
                        killCell();
                        break;
                }
                break;
            case 1:
                switch(numNeighbors)
                {
                    case 0: case 1: case 4: case 5: case 6: case 7: case 8:
                        killCell();
                        break;
                    case 2: case 3:
                        createCell();
                        break;
                }
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



}
