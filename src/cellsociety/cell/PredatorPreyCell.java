package cellsociety.cell;

public class PredatorPreyCell extends Cell{

    private int chrononCounter=0;
    private int energy=5;

    public PredatorPreyCell(int i, int j, int initialState, int rows, int cols) throws IllegalCellStateException
    {
        super(i,j,initialState,rows,cols);
        if(initialState>3 || initialState <0){
            throw new IllegalCellStateException();
        }
    }



    public void updateChronon()
    {
        chrononCounter++;
    }

    public void resetChronon()
    {
        chrononCounter=0;
    }

    public void gainEnergy()
    {
        energy++;
    }

    public void loseEnergy()
    {
        energy--;
    }


    //Testing Purposes
    public void setChrononCounter(int chrononCounter) {this.chrononCounter = chrononCounter;}

    public int getChrononCounter() {return chrononCounter;}

    public int getEnergy() {return energy;}

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
