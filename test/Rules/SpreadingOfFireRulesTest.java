package Rules;

import cellsociety.Rule.Rules;
import cellsociety.Rule.SpreadingOfFireRules;
import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cellsociety.cell.FireCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpreadingOfFireRulesTest {

    @Test
    void treeIsBurned()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        FireCell cell= new FireCell(1,1,1);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,2));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 2);
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
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        FireCell cell= new FireCell(1,1,0);
        List<Double> args = new ArrayList<>();
        args.add(0.0);
        args.add(1.0);
        Rules rules=new SpreadingOfFireRules(cell, args);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void treeStaysAlive()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        FireCell cell= new FireCell(1,1,1);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,1));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 2);
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
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        FireCell cell= new FireCell(1,1,0);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,2));
        allCells.add(new Cell(1,0,1));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 2);
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
