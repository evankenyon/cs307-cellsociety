package cellsociety.Rule;

import cellsociety.CellParts.Cell;

public class GameOfLifeRules {
    private Cell cell;
    private int cellCurrentState;
    private int numNeighbors;

    public GameOfLifeRules(Cell cell)
    {
        this.cell=cell;
        numNeighbors=cell.getNeighbors().size();
        cellCurrentState=cell.getCurrentState();
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
