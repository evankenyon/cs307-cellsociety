package Rules;

import cellsociety.Rule.GameOfLifeRules;
import cellsociety.cell.Cell;
import cellsociety.cell.GameOfLifeCell;
import java.lang.reflect.InvocationTargetException;

import cellsociety.cell.IllegalCellStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeRulesTest {
    @Test
    void killCellTest() throws IllegalCellStateException {
        GameOfLifeCell cell=new GameOfLifeCell(0,0,1,10,10);
        List<Cell> test=new ArrayList<>();
        test.add(new GameOfLifeCell(0,0,0,10,10));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setCellState(0);
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void createCellTest() throws IllegalCellStateException {
        GameOfLifeCell cell=new GameOfLifeCell(0,0,0,10,10);
        List<Cell> test=new ArrayList<>();
        test.add(new GameOfLifeCell(0,0,0,10,10));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setCellState(1);
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLessThanTwoNeighbors()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        GameOfLifeCell cell=new GameOfLifeCell(0,0,1,10,10);
        List<Cell> test=new ArrayList<>();
        test.add(new GameOfLifeCell(0,0,0,10,10));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setNumOneNeighbors(1);
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void setStateLiveTwoNeighbors()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        GameOfLifeCell cell=new GameOfLifeCell(0,0,1,10,10);
        List<GameOfLifeCell> allCells = new ArrayList<>();
        allCells.add(new GameOfLifeCell(0,1,1,10,10));
        allCells.add(new GameOfLifeCell(1,0,1,10,10));
        for(GameOfLifeCell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 1);
        }
        cell.updateCellNeighborStateMap();
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLiveThreeNeighbors()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        GameOfLifeCell cell=new GameOfLifeCell(0,0,1,10,10);
        List<GameOfLifeCell> allCells = new ArrayList<>();
        allCells.add(new GameOfLifeCell(0,1,1,10,10));
        allCells.add(new GameOfLifeCell(1,0,1,10,10));
        allCells.add(new GameOfLifeCell(1,1,1,10,10));
        for(GameOfLifeCell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 1);
        }
        cell.updateCellNeighborStateMap();
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLiveMoreThanThreeNeighbors()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        GameOfLifeCell cell=new GameOfLifeCell(1,0,1,10,10);
        List<GameOfLifeCell> allCells = new ArrayList<>();
        allCells.add(new GameOfLifeCell(0,1,1,10,10));
        allCells.add(new GameOfLifeCell(0,0,1,10,10));
        allCells.add(new GameOfLifeCell(1,1,1,10,10));
        allCells.add(new GameOfLifeCell(2,1,1,10,10));
        for(GameOfLifeCell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 1);
        }
        cell.updateCellNeighborStateMap();
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void setStateDeadThreeNeighbors()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        GameOfLifeCell cell=new GameOfLifeCell(0,0,0,10,10);
        List<GameOfLifeCell> allCells = new ArrayList<>();
        allCells.add(new GameOfLifeCell(0,1,1,10,10));
        allCells.add(new GameOfLifeCell(1,0,1,10,10));
        allCells.add(new GameOfLifeCell(1,1,1,10,10));
        for(GameOfLifeCell otherCell : allCells) {
            cell.updateNeighbors(otherCell, 1);
        }
        cell.updateCellNeighborStateMap();
        GameOfLifeRules rules=new GameOfLifeRules(cell, new ArrayList<>());
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateBasicBlinker()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalCellStateException {
        List<GameOfLifeCell> allCells = new ArrayList<>();
        allCells.add(new GameOfLifeCell(1, 1, 1,10,10));
        allCells.add(new GameOfLifeCell(1, 0, 1,10,10));
        allCells.add(new GameOfLifeCell(1, 2, 1,10,10));
        allCells.add(new GameOfLifeCell(0, 1, 0,10,10));
        allCells.add(new GameOfLifeCell(2, 1, 0,10,10));
        for (GameOfLifeCell cell: allCells) {
            for (GameOfLifeCell otherCell: allCells) {
                cell.updateNeighbors(otherCell, 1);
            }
        }
        for (GameOfLifeCell cell : allCells) {
            cell.updateCellNeighborStateMap();
        }
        for(GameOfLifeCell cell : allCells) {
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
