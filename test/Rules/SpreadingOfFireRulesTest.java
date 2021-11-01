package Rules;

import cellsociety.Rule.Rules;
import cellsociety.Rule.SpreadingOfFireRules;
import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cellsociety.cell.FireCell;
import cellsociety.cell.IllegalCellStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpreadingOfFireRulesTest {
    private List<Integer> defaultNumCornersShared;

    @BeforeEach
    void setUp() {
        defaultNumCornersShared = new ArrayList<>();
        defaultNumCornersShared.add(2);
    }

    @Test
    void treeIsBurned()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        FireCell cell= new FireCell(1,1,1,10,10);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,2,10,10));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, defaultNumCornersShared);
        }
        cell.updateCellNeighborStateMap();
        List<Double> args = new ArrayList<>();
        args.add(1.0);
        args.add(0.0);
        Rules rules=new SpreadingOfFireRules(cell, args);
        rules.setState();
        Assertions.assertEquals(2,cell.getFutureState());
    }

    @Test
    void treeGrows()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        FireCell cell= new FireCell(1,1,0,10,10);
        List<Double> args = new ArrayList<>();
        args.add(0.0);
        args.add(1.0);
        Rules rules=new SpreadingOfFireRules(cell, args);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void treeStaysAlive()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        FireCell cell= new FireCell(1,1,1,10,10);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,1,10,10));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, defaultNumCornersShared);
        }
        cell.updateCellNeighborStateMap();
        List<Double> args = new ArrayList<>();
        args.add(1.0);
        args.add(0.0);
        Rules rules=new SpreadingOfFireRules(cell, args);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void treeStaysDead()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        FireCell cell= new FireCell(1,1,0,10,10);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,2,10,10));
        allCells.add(new Cell(1,0,1,10,10));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, defaultNumCornersShared);
        }
        cell.updateCellNeighborStateMap();
        List<Double> args = new ArrayList<>();
        args.add(1.0);
        args.add(0.0);
        Rules rules=new SpreadingOfFireRules(cell, args);
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }
}
