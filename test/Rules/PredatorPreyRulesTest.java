package Rules;

import cellsociety.Rule.PredatorPreyRules;
import cellsociety.cell.Cell;
import cellsociety.cell.IllegalCellStateException;
import cellsociety.cell.PredatorPreyCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PredatorPreyRulesTest {

    @Test
    void checkReproduction() throws IllegalCellStateException {
        PredatorPreyCell cell=new PredatorPreyCell(0,0,1,10,10);
        cell.setChrononCounter(3);
        PredatorPreyRules pp=new PredatorPreyRules(cell, new ArrayList<>());
        pp.checkReproduction();
        Assertions.assertTrue(cell.getFutureState()==1 && cell.getChrononCounter()==0);
    }

    @Test
    void deadShark() throws IllegalCellStateException {
        PredatorPreyCell cell=new PredatorPreyCell(0,0,2,10,10);
        cell.setEnergy(0);
        PredatorPreyRules pp=new PredatorPreyRules(cell, new ArrayList<>());
        pp.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void sharkEatsFish() throws IllegalCellStateException {
        PredatorPreyCell cell=new PredatorPreyCell(1,1,2,10,10);
        cell.setEnergy(3);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0,10,10));
        test.add(new Cell(1,0,0,10,10));
        test.add(new Cell(0,1,1,10,10));
        test.add(new Cell(1,2,0,10,10));
        for (Cell otherCell : test) {
            cell.updateNeighbors(otherCell, List.of(new Integer[]{1, 2}));
        }
        cell.updateCellNeighborStateMap();
        PredatorPreyRules pp=new PredatorPreyRules(cell, new ArrayList<>());
        pp.setState();
        Assertions.assertTrue(cell.getFutureState()==0 && cell.getNeighborOfState(1, 0).getFutureState()==2);
    }
}
