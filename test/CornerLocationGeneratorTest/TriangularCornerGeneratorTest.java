package CornerLocationGeneratorTest;

import cellsociety.CornerLocationGenerator.TriangleCellCornerLocationGenerator;
import cellsociety.location.CornerLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TriangularCornerGeneratorTest {
    private List<CornerLocation> l;

    @Test
    void TopCornerTestEvenIndex()
    {
        TriangleCellCornerLocationGenerator r= new TriangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(0).getX_pos()==50 && l.get(0).getY_pos()==0);
    }

    @Test
    void BottomRightCornerTestEvenIndex()
    {
        TriangleCellCornerLocationGenerator r= new TriangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(1).getX_pos()==100 && l.get(1).getY_pos()==100);
    }

    @Test
    void BottomLeftCornerTestEvenIndex()
    {
        TriangleCellCornerLocationGenerator r= new TriangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(2).getX_pos()==0 && l.get(2).getY_pos()==100);
    }

    @Test
    void BottomCornerTestOddIndex()
    {
        TriangleCellCornerLocationGenerator r= new TriangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,1);
        Assertions.assertTrue(l.get(0).getX_pos()==100 && l.get(0).getY_pos()==100);
    }

    @Test
    void TopLeftCornerTestOddIndex()
    {
        TriangleCellCornerLocationGenerator r= new TriangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,1);
        Assertions.assertTrue(l.get(1).getX_pos()==50 && l.get(1).getY_pos()==0);
    }

    @Test
    void TopRightCornerTestOddIndex()
    {
        TriangleCellCornerLocationGenerator r= new TriangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,1);
        Assertions.assertTrue(l.get(2).getX_pos()==150 && l.get(2).getY_pos()==0);
    }
}
