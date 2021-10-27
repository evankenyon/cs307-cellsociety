package Rules;

import cellsociety.Rule.GameOfLifeRules;
import cellsociety.cell.Cell;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeRulesTest {
    @Test
    void killCellTest()
    {
        Cell cell=new Cell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setCellStateZero();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void createCellTest()
    {
        Cell cell=new Cell(0,0,0);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setCellStateOne();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLessThanTwoNeighbors()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell=new Cell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setNumOneNeighbors(1);
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void setStateLiveTwoNeighbors()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell=new Cell(0,0,1);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,1));
        allCells.add(new Cell(1,0,1));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 1);
        }
        cell.updateCellNeighborStates();
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLiveThreeNeighbors()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell=new Cell(0,0,1);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,1));
        allCells.add(new Cell(1,0,1));
        allCells.add(new Cell(1,1,1));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 1);
        }
        cell.updateCellNeighborStates();
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLiveMoreThanThreeNeighbors()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell=new Cell(1,0,1);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,1));
        allCells.add(new Cell(0,0,1));
        allCells.add(new Cell(1,1,1));
        allCells.add(new Cell(2,1,1));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 1);
        }
        cell.updateCellNeighborStates();
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void setStateDeadThreeNeighbors()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Cell cell=new Cell(0,0,0);
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(0,1,1));
        allCells.add(new Cell(1,0,1));
        allCells.add(new Cell(1,1,1));
        for(Cell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 1);
        }
        cell.updateCellNeighborStates();
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateBasicBlinker()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(1, 1, 1));
        allCells.add(new Cell(1, 0, 1));
        allCells.add(new Cell(1, 2, 1));
        allCells.add(new Cell(0, 1, 0));
        allCells.add(new Cell(2, 1, 0));
        for (Cell cell: allCells) {
            for (Cell otherCell: allCells) {
                cell.updateNeighbors(otherCell, 1);
            }
        }
        for (Cell cell : allCells) {
            cell.updateCellNeighborStates();
        }
        for(Cell cell : allCells) {
            GameOfLifeRules rules = new GameOfLifeRules(cell, new ArrayList<>());
            rules.setState();
        }

        Assertions.assertEquals(1, allCells.get(0).getFutureState());
        Assertions.assertEquals(0, allCells.get(1).getFutureState());
        Assertions.assertEquals(0, allCells.get(2).getFutureState());
        Assertions.assertEquals(1, allCells.get(3).getFutureState());
        Assertions.assertEquals(1, allCells.get(4).getFutureState());

    }
}
