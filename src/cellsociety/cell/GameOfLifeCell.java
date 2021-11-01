package cellsociety.cell;

public class GameOfLifeCell extends Cell{


    public GameOfLifeCell(int i, int j, int initialState, int rows, int cols)throws IllegalCellStateException {
        super(i,j,initialState,rows, cols);
        if(initialState>1 || initialState <0){
            throw new IllegalCellStateException();
        }
    }





}
