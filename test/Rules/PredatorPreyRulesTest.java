package Rules;

import cellsociety.Rule.PredatorPreyRules;
import cellsociety.Rule.SegregationRules;
import cellsociety.cell.Cell;
import cellsociety.cell.PredatorPreyCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PredatorPreyRulesTest {

    @Test
    void checkReproduction()
    {
        PredatorPreyCell cell=new PredatorPreyCell(0,0,1);
        cell.setChrononCounter(3);
        PredatorPreyRules pp=new PredatorPreyRules(cell, new ArrayList<>());
        pp.checkReproduction();
        Assertions.assertTrue(cell.getFutureState()==1 && cell.getChrononCounter()==0);
    }

    @Test
    void deadShark()
    {
        PredatorPreyCell cell=new PredatorPreyCell(0,0,2);
        cell.setEnergy(0);
        PredatorPreyRules pp=new PredatorPreyRules(cell, new ArrayList<>());
        pp.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void sharkEatsFish()
    {
        PredatorPreyCell cell=new PredatorPreyCell(0,0,2);
        cell.setEnergy(3);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        test.add(new Cell(0,0,0));
        test.add(new Cell(0,0,1));
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        cell.updateCellNeighborStates();
        PredatorPreyRules pp=new PredatorPreyRules(cell, new ArrayList<>());
        pp.setState();
        Assertions.assertTrue(cell.getFutureState()==0 && cell.getNeighborOfState(1, 0).getFutureState()==2);
    }
}
