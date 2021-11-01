package cellsociety.Rule;

import cellsociety.cell.Cell;
import cellsociety.cell.PercolationCell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;

public class PercolationRules extends Rules {
    private static final String DEFAULT_RESOURCE_PACKAGE =
        GameOfLifeRules.class.getPackageName() + ".resources.";
    private static final String STATE_AND_NEIGHBORS_MAP_FILENAME = "PercolationRules";
    //private PercolationCell pcell;

    public PercolationRules(Cell cell, List<Double> args)
    {
        super(cell);
        //pcell=cell;
        if(!args.isEmpty()) {
            //TODO: actually handle
            throw new InputMismatchException();
        }
        stateAndNeighborsMap = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STATE_AND_NEIGHBORS_MAP_FILENAME);
    }
}
