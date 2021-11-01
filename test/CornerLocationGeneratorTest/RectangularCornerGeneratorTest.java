package CornerLocationGeneratorTest;

import cellsociety.CornerLocationGenerator.RectangleCellCornerLocationGenerator;
import cellsociety.location.CornerLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RectangularCornerGeneratorTest {

    private List<CornerLocation> l;

    @Test
    void TopLeftCornerTest0Index()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(0).getX_pos()==0 && l.get(0).getY_pos()==0);
    }

    @Test
    void TopRightCornerTest0Index()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(1).getX_pos()==100 && l.get(1).getY_pos()==0);
    }

    @Test
    void BottomLeftCornerTest0Index()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(3).getX_pos()==0 && l.get(2).getY_pos()==100);
    }

    @Test
    void BottomRightCornerTest0Index()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(0,0);
        Assertions.assertTrue(l.get(2).getX_pos()==100 && l.get(3).getY_pos()==100);
    }

    @Test
    void TopLeftCornerTestRandIndex()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(3,2);
        Assertions.assertTrue(l.get(0).getX_pos()==200 && l.get(0).getY_pos()==300);
    }

    @Test
    void TopRightCornerTestRandIndex()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(3,2);
        Assertions.assertTrue(l.get(1).getX_pos()==300 && l.get(1).getY_pos()==300);
    }

    @Test
    void BottomLeftCornerTestRandIndex()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(3,2);
        Assertions.assertTrue(l.get(3).getX_pos()==200 && l.get(2).getY_pos()==400);
    }

    @Test
    void BottomRightCornerTestRandIndex()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(3,2);
        Assertions.assertTrue(l.get(2).getX_pos()==300 && l.get(3).getY_pos()==400);
    }

    @Test
    void TopLeftCornerTestMaxIndex()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(7,7);
        Assertions.assertTrue(l.get(0).getX_pos()==700 && l.get(0).getY_pos()==700);
    }

    @Test
    void TopRightCornerTestMaxIndex()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(7,7);
        Assertions.assertTrue(l.get(1).getX_pos()==800 && l.get(1).getY_pos()==700);
    }

    @Test
    void BottomLeftCornerTestMaxIndex()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(7,7);
        Assertions.assertTrue(l.get(3).getX_pos()==700 && l.get(2).getY_pos()==800);
    }

    @Test
    void BottomRightCornerTestMaxIndex()
    {
        RectangleCellCornerLocationGenerator r= new RectangleCellCornerLocationGenerator(4,4);
        l=r.generateCorners(7,7);
        Assertions.assertTrue(l.get(2).getX_pos()==800 && l.get(3).getY_pos()==800);
    }


}
