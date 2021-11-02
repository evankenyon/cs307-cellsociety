package CornerLocationGeneratorTest;

import cellsociety.CornerLocationGenerator.HexagonCellCornerLocationGenerator;
import cellsociety.location.CornerLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HexagonalCornerGeneratorTest {
    private List<CornerLocation> l;
    private double ROUND_VALUE=100.0;

    @Test
    void TopCornerTestEvenIndex()
    {
        HexagonCellCornerLocationGenerator r= new HexagonCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(Math.round(ROUND_VALUE*l.get(0).getX_pos())/ROUND_VALUE==43.30 && l.get(0).getY_pos()==0);
    }

    @Test
    void BottomRightCornerTestEvenIndex()
    {
        HexagonCellCornerLocationGenerator r= new HexagonCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(Math.round(ROUND_VALUE*l.get(4).getX_pos())/ROUND_VALUE==86.60
                && l.get(4).getY_pos()==62.5);
    }

    @Test
    void BottomLeftCornerTestEvenIndex()
    {
        HexagonCellCornerLocationGenerator r= new HexagonCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(2).getX_pos()==0 && l.get(2).getY_pos()==62.5);
    }

    @Test
    void TopRightCornerTestEvenIndex()
    {
        HexagonCellCornerLocationGenerator r= new HexagonCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(Math.round(ROUND_VALUE*l.get(5).getX_pos())/ROUND_VALUE==86.60
                && l.get(5).getY_pos()==37.5);
    }

    @Test
    void TopLeftCornerTestEvenIndex()
    {
        HexagonCellCornerLocationGenerator r= new HexagonCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(1).getX_pos()==0 && l.get(1).getY_pos()==37.5);
    }

    @Test
    void BottomCornerTestEvenIndex()
    {
        HexagonCellCornerLocationGenerator r= new HexagonCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(Math.round(ROUND_VALUE*l.get(3).getX_pos())/ROUND_VALUE==43.30
                && l.get(3).getY_pos()==100);
    }

    @Test
    void TopCornerTestOddIndex()
    {
        HexagonCellCornerLocationGenerator r= new HexagonCellCornerLocationGenerator(4,4);
        l=r.generateCorners(1,0);
        Assertions.assertTrue(Math.round(ROUND_VALUE*l.get(0).getX_pos())/ROUND_VALUE==86.60
                && l.get(0).getY_pos()==62.5);
    }


    @Test
    void TopLeftCornerTestOddIndex()
    {
        HexagonCellCornerLocationGenerator r= new HexagonCellCornerLocationGenerator(4,4);
        l=r.generateCorners(1,0);
        System.out.println(Math.round(ROUND_VALUE*l.get(1).getX_pos())/ROUND_VALUE);
        Assertions.assertTrue(Math.round(ROUND_VALUE*l.get(1).getX_pos())/ROUND_VALUE==43.30
                && l.get(1).getY_pos()==100);
    }

}
