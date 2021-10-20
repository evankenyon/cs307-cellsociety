package Rules;

import cellsociety.Rule.GameOfLifeRules;
import cellsociety.cell.Cell;
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
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.killCell();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void createCellTest()
    {
        Cell cell=new Cell(0,0,0);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.createCell();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLessThanTwoNeighbors()
    {
        Cell cell=new Cell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(1);
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void setStateLiveTwoNeighbors()
    {
        Cell cell=new Cell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(2);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLiveThreeNeighbors()
    {
        Cell cell=new Cell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(3);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLiveMoreThanThreeNeighbors()
    {
        Cell cell=new Cell(0,0,1);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(4);
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void setStateDeadThreeNeighbors()
    {
        Cell cell=new Cell(0,0,0);
        List<Cell> test=new ArrayList<>();
        test.add(new Cell(0,0,0));
        cell.setNeighbors(test);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(3);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateBasicBlinker() {
        List<Cell> allCells = new ArrayList<>();
        allCells.add(new Cell(1, 1, 1));
        allCells.add(new Cell(1, 0, 1));
        allCells.add(new Cell(1, 2, 1));
        allCells.add(new Cell(0, 1, 0));
        allCells.add(new Cell(2, 1, 0));
        for (Cell cell: allCells) {
            for (Cell otherCell: allCells) {
                cell.updateNeighbors(otherCell);
            }
        }
        for(Cell cell : allCells) {
            GameOfLifeRules rules = new GameOfLifeRules(cell);
            rules.setState();
        }

        Assertions.assertEquals(1, allCells.get(0).getFutureState());
        Assertions.assertEquals(0, allCells.get(1).getFutureState());
        Assertions.assertEquals(0, allCells.get(2).getFutureState());
        Assertions.assertEquals(1, allCells.get(3).getFutureState());
        Assertions.assertEquals(1, allCells.get(4).getFutureState());

    }
}
