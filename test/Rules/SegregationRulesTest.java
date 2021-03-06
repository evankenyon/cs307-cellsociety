package Rules;

import cellsociety.Rule.SegregationRules;
import cellsociety.cell.Cell;
import cellsociety.cell.IllegalCellStateException;
import cellsociety.cell.SegregationCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SegregationRulesTest {
    @Test
    void cellIsEmptyTest() throws IllegalCellStateException {
        SegregationCell cell=new SegregationCell(0,0,0,10,10);
        SegregationRules sr=new SegregationRules(cell, new ArrayList<>());
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void checkFutureStateCurrentCellSatisfied() throws IllegalCellStateException {
        SegregationCell cell=new SegregationCell(1,1,1,10,10);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(1,0,1,10,10));
        test.add(new Cell(0,1,1,10,10));
        test.add(new Cell(2,1,1,10,10));
        test.add(new Cell(1,2,2,10,10));
        for (Cell otherCell : test) {
            cell.updateNeighbors(otherCell, List.of(new Integer[]{1, 2}));
        }
        cell.updateCellNeighborStateMap();
        cell.updateCellNeighborStateMap();
        SegregationRules sr=new SegregationRules(cell, new ArrayList<>());
        sr.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void checkFutureStateCurrentCellNotSatisfied() throws IllegalCellStateException {
        SegregationCell cell=new SegregationCell(1,1,1,10,10);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,2,10,10));
        test.add(new Cell(1,0,0,10,10));
        test.add(new Cell(0,1,1,10,10));
        test.add(new Cell(2,1,2,10,10));
        test.add(new Cell(2,2,2,10,10));
        for (Cell otherCell : test) {
            cell.updateNeighbors(otherCell, List.of(new Integer[]{1, 2}));
        }
        cell.updateCellNeighborStateMap();
        cell.updateCellNeighborStateMap();
        SegregationRules sr=new SegregationRules(cell, new ArrayList<>());
        sr.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void checkFutureStateNeighborsNotSatisfied() throws IllegalCellStateException {
        SegregationCell cell=new SegregationCell(1,1,1,10,10);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,2,10,10));
        test.add(new Cell(1,0,0,10,10));
        test.add(new Cell(0,1,1,10,10));
        test.add(new Cell(1,2,2,10,10));
        test.add(new Cell(2,2,2,10,10));
        for (Cell otherCell : test) {
            cell.updateNeighbors(otherCell, List.of(new Integer[]{1, 2}));
        }
        cell.updateCellNeighborStateMap();
        SegregationRules sr=new SegregationRules(cell, new ArrayList<>());
        sr.setState();
        cell.updateCellNeighborStateMap();
        Assertions.assertEquals(1,cell.numOfStateNeighbors(0));
    }


}
