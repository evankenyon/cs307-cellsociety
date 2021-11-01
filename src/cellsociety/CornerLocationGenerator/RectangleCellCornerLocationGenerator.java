package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;

import java.util.List;

public class RectangleCellCornerLocationGenerator extends CornerLocationGenerator {
    private double height=getVIEW_HEIGHT()/rows;
    private double length=getVIEW_WIDTH()/columns;

    public RectangleCellCornerLocationGenerator(int rows, int columns)
    {
        super(rows,columns);
    }

    @Override
    public List<CornerLocation> generateCorners(int i, int j)
    {
        corners.clear();
        CornerLocation topLeft=new CornerLocation(j*length,i*height);
        CornerLocation topRight=new CornerLocation((j+1)*length,i*height);
        CornerLocation bottomLeft=new CornerLocation((j)*length,(i+1)*height);
        CornerLocation bottomRight=new CornerLocation((j+1)*length,(i+1)*height);
        corners.add(topLeft);
        corners.add(topRight);
        corners.add(bottomRight);
        corners.add(bottomLeft);
        return corners;
    }
}
