package Rules;

import cellsociety.Rule.PredatorPreyRules;
import cellsociety.Rule.SegregationRules;
import cellsociety.cell.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PredatorPreyRulesTest {

    @Test
    void checkReproduction()
    {
        Cell cell=new Cell(0,0,1);
        cell.setChrononCounter(3);
        PredatorPreyRules pp=new PredatorPreyRules(cell);
        pp.checkReproduction();
        Assertions.assertTrue(cell.getFutureState()==1 && cell.getChrononCounter()==0);
    }

    @Test
    void deadShark()
    {
        Cell cell=new Cell(0,0,2);
        cell.setEnergy(0);
        PredatorPreyRules pp=new PredatorPreyRules(cell);
        pp.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void sharkEatsFish()
    {
        Cell cell=new Cell(0,0,2);
        cell.setEnergy(3);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        test.add(new Cell(0,0,0));
        test.add(new Cell(0,0,1));
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        cell.updateCellNeighborStates();
        //System.out.println(cell.getNeighborCellStateMap());
        PredatorPreyRules pp=new PredatorPreyRules(cell);
        pp.setState();
        Assertions.assertTrue(cell.getFutureState()==0 && cell.getNeighborOfState(1, 0).getFutureState()==2);
    }
}
