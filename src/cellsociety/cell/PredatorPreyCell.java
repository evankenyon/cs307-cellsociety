package cellsociety.cell;

public class PredatorPreyCell extends Cell{

    private int chrononCounter=0;
    private int energy=5;

    public PredatorPreyCell(int i, int j, int initialState, int rows, int columns)
    {
        super(i,j,initialState,rows,columns);
    }

    public PredatorPreyCell(int i, int j, int initialState)
    {
        super(i,j,initialState);
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


    public void setChrononCounter(int chrononCounter) {this.chrononCounter = chrononCounter;}

    public int getChrononCounter() {return chrononCounter;}

    public int getEnergy() {return energy;}

}
