package Rules;

import cellsociety.Rule.GameOfLifeRules;
import cellsociety.cell.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameOfLifeRulesTest {
    @Test
    void killCellTest()
    {
        Cell cell=new Cell(0,0,1);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.killCell();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void createCellTest()
    {
        Cell cell=new Cell(0,0,0);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.createCell();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLessThanTwoNeighbors()
    {
        Cell cell=new Cell(0,0,1);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(1);
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void setStateLiveTwoNeighbors()
    {
        Cell cell=new Cell(0,0,1);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(2);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLiveThreeNeighbors()
    {
        Cell cell=new Cell(0,0,1);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(3);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }

    @Test
    void setStateLiveMoreThanThreeNeighbors()
    {
        Cell cell=new Cell(0,0,1);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(4);
        rules.setState();
        Assertions.assertEquals(0,cell.getFutureState());
    }

    @Test
    void setStateDeadThreeNeighbors()
    {
        Cell cell=new Cell(0,0,0);
        GameOfLifeRules rules=new GameOfLifeRules(cell);
        rules.setNumNeighbors(3);
        rules.setState();
        Assertions.assertEquals(1,cell.getFutureState());
    }
}
