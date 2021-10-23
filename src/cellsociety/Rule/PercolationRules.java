package cellsociety.Rule;

import cellsociety.cell.Cell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

public class PercolationRules extends Rules {
    private static final String DEFAULT_RESOURCE_PACKAGE =
        GameOfLifeRules.class.getPackageName() + ".resources.";
    private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = "PercolationRules";
    private List<Cell> neighbors;

    public PercolationRules(Cell cell)
    {
        super(cell);
        stateAndNeighborsMap = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STATE_AND_NEIGHBORS_MAP_FILENAME);
        this.neighbors=cell.getNeighbors();
    }

    private void percolate()
    {
        int count=0;
        for (Cell neighbor: neighbors)
        {
            if (neighbor.getCurrentState()==1)
            {
                count++;
            }
        }
        if (count==0){cell.setFutureState(1);}
        else {cell.setFutureState(2);}
    }


}
