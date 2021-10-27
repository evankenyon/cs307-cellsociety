package Rules;

import cellsociety.Rule.GameOfLifeRules;
import cellsociety.Rule.PercolationRules;
import cellsociety.Rule.Rules;
import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PercolationRulesTest {

    @Test
    void zeroStaysZero()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell= new Cell(1,1,0);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,1));
        allCells.add(new Cell(1,0,1));
        allCells.add(new Cell(1, 2,1));
        allCells.add(new Cell(2, 1,1));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 2);
        }
        cell.updateCellNeighborStates();
        Rules rules=new PercolationRules(cell);
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void oneStaysOne()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell= new Cell(1,1,1);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,0));
        allCells.add(new Cell(1,0,0));
        allCells.add(new Cell(1, 2,0));
        allCells.add(new Cell(2, 1,0));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 2);
        }
        cell.updateCellNeighborStates();
        Rules rules=new PercolationRules(cell);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void oneTurnsTwo()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell= new Cell(1,1,1);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,1));
        allCells.add(new Cell(1,0,1));
        allCells.add(new Cell(1, 2,1));
        allCells.add(new Cell(2, 1,0));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 2);
        }
        cell.updateCellNeighborStates();
        Rules rules=new PercolationRules(cell);
        rules.setState();
        Assertions.assertEquals(2,cell.getFutureState());
    }

    @Test
    void twoStaysTwo()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell= new Cell(1,1,2);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,0));
        allCells.add(new Cell(1,0,1));
        allCells.add(new Cell(1, 2,0));
        allCells.add(new Cell(2, 1,1));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 2);
        }
        cell.updateCellNeighborStates();
        Rules rules=new PercolationRules(cell);
        rules.setState();
        Assertions.assertEquals(2,cell.getFutureState());
    }
}
