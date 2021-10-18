package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;

import java.util.List;

public class SquareCellCornerLocationGenerator extends CornerLocationGenerator {
    private double sideALength=VIEW_HEIGHT/rows;
    private double sideBLength=VIEW_WIDTH/columns;

    public SquareCellCornerLocationGenerator(int i, int j)
    {
        super(i,j);
    }

    public List<CornerLocation> generateCorners(int i, int j)
    {

    }
}
