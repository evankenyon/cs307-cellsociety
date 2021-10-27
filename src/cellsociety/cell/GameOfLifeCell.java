package cellsociety.cell;

public class GameOfLifeCell extends Cell{
    public GameOfLifeCell(int i, int j, int initialState, int rows, int columns)
    {
        super(i,j,initialState,rows,columns);
    }

    public GameOfLifeCell(int i, int j, int initialState)
    {
        super(i,j,initialState);
    }
}
