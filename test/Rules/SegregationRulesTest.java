package Rules;

import cellsociety.Rule.SegregationRules;
import cellsociety.cell.Cell;
import cellsociety.cell.SegregationCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SegregationRulesTest {
    @Test
    void cellIsEmptyTest()
    {
        SegregationCell cell=new SegregationCell(0,0,0);
        SegregationRules sr=new SegregationRules(cell, new ArrayList<>());
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void checkFutureStateCurrentCellSatisfied()
    {
        SegregationCell cell=new SegregationCell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,1));
        test.add(new Cell(0,0,1));
        test.add(new Cell(0,0,1));
        test.add(new Cell(0,0,2));
        cell.setNeighbors(test);
        cell.updateCellNeighborStateMap();
        SegregationRules sr=new SegregationRules(cell, new ArrayList<>());
        sr.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void checkFutureStateCurrentCellNotSatisfied()
    {
        SegregationCell cell=new SegregationCell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,2));
        test.add(new Cell(0,0,0));
        test.add(new Cell(0,0,1));
        test.add(new Cell(0,0,2));
        test.add(new Cell(0,0,2));
        cell.setNeighbors(test);
        cell.updateCellNeighborStateMap();
        SegregationRules sr=new SegregationRules(cell, new ArrayList<>());
        sr.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void checkFutureStateNeighborsNotSatisfied()
    {
        SegregationCell cell=new SegregationCell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,2));
        test.add(new Cell(0,0,0));
        test.add(new Cell(0,0,1));
        test.add(new Cell(0,0,2));
        test.add(new Cell(0,0,2));
        cell.setNeighbors(test);
        cell.updateCellNeighborStateMap();
        SegregationRules sr=new SegregationRules(cell, new ArrayList<>());
        sr.setState();
        cell.updateCellNeighborStateMap();
        Assertions.assertEquals(1,cell.numOfStateNeighbors(0));
    }


}
