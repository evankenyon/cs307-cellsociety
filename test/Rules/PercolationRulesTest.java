package Rules;

import cellsociety.Rule.PercolationRules;
import cellsociety.Rule.Rules;
import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cellsociety.cell.IllegalCellStateException;
import cellsociety.cell.PercolationCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PercolationRulesTest {
    private List<Integer> defaultNumCornersShared;

    @BeforeEach
    void setUp() {
        defaultNumCornersShared = new ArrayList<>();
        defaultNumCornersShared.add(2);
    }

    @Test
    void zeroStaysZero()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        PercolationCell cell= new PercolationCell(1,1,0,10,10);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,0,10,10));
        allCells.add(new Cell(1,0,0,10,10));
        allCells.add(new Cell(1, 2,0,10,10));
        allCells.add(new Cell(2, 1,0,10,10));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, defaultNumCornersShared);
        }
        cell.updateCellNeighborStateMap();
        Rules rules=new PercolationRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void zeroTurnsOne()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        PercolationCell cell= new PercolationCell(1,1,0,10,10);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,0,10,10));
        allCells.add(new Cell(1,0,1,10,10));
        allCells.add(new Cell(1, 2,1,10,10));
        allCells.add(new Cell(2, 1,0,10,10));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, defaultNumCornersShared);
        }
        cell.updateCellNeighborStateMap();
        Rules rules=new PercolationRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void oneStaysOne()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        PercolationCell cell= new PercolationCell(1,1,1, 10,10);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,0,10,10));
        allCells.add(new Cell(1,0,0,10,10));
        allCells.add(new Cell(1, 2,0,10,10));
        allCells.add(new Cell(2, 1,0,10,10));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, defaultNumCornersShared);
        }
        cell.updateCellNeighborStateMap();
        Rules rules=new PercolationRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void twoStaysTwo()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        PercolationCell cell= new PercolationCell(1,1,2,10,10);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,0,10,10));
        allCells.add(new Cell(1,0,1,10,10));
        allCells.add(new Cell(1, 2,0,10,10));
        allCells.add(new Cell(2, 1,1,10,10));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, defaultNumCornersShared);
        }
        cell.updateCellNeighborStateMap();
        Rules rules=new PercolationRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(2,cell.getFutureState());
    }
}
