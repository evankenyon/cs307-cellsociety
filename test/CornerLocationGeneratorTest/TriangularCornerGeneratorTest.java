package CornerLocationGeneratorTest;

import cellsociety.CornerLocationGenerator.TriangularCellCornerLocationGenerator;
import cellsociety.location.CornerLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TriangularCornerGeneratorTest {
    private List<CornerLocation> l;

    @Test
    void TopCornerTestEvenIndex()
    {
        TriangularCellCornerLocationGenerator r= new TriangularCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(0).getX_pos()==50 && l.get(0).getY_pos()==0);
    }

    @Test
    void BottomRightCornerTestEvenIndex()
    {
        TriangularCellCornerLocationGenerator r= new TriangularCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(1).getX_pos()==100 && l.get(1).getY_pos()==100);
    }

    @Test
    void BottomLeftCornerTestEvenIndex()
    {
        TriangularCellCornerLocationGenerator r= new TriangularCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(2).getX_pos()==0 && l.get(2).getY_pos()==100);
    }

    @Test
    void BottomCornerTestOddIndex()
    {
        TriangularCellCornerLocationGenerator r= new TriangularCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,1);
        Assertions.assertTrue(l.get(0).getX_pos()==100 && l.get(0).getY_pos()==100);
    }

    @Test
    void TopLeftCornerTestOddIndex()
    {
        TriangularCellCornerLocationGenerator r= new TriangularCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,1);
        Assertions.assertTrue(l.get(1).getX_pos()==50 && l.get(1).getY_pos()==0);
    }

    @Test
    void TopRightCornerTestOddIndex()
    {
        TriangularCellCornerLocationGenerator r= new TriangularCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,1);
        Assertions.assertTrue(l.get(2).getX_pos()==150 && l.get(2).getY_pos()==0);
    }
}
