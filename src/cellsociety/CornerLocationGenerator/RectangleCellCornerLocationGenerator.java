package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;

import java.util.List;

public class RectangleCellCornerLocationGenerator extends CornerLocationGenerator {
    private double height=VIEW_HEIGHT/rows;
    private double length=VIEW_WIDTH/columns;

    public RectangleCellCornerLocationGenerator(int rows, int columns)
    {
        super(rows,columns);
    }

    public List<CornerLocation> generateCorners(int i, int j)
    {
        CornerLocation topLeft=new CornerLocation(i*length,j*height);
        CornerLocation topRight=new CornerLocation((i+1)*length,j*height);
        CornerLocation bottomLeft=new CornerLocation(i*length,(j+1)*height);
        CornerLocation bottomRight=new CornerLocation((i+1)*length,(j+1)*height);
        corners.add(topLeft);
        corners.add(topRight);
        corners.add(bottomLeft);
        corners.add(bottomRight);
        return corners;
    }
}
