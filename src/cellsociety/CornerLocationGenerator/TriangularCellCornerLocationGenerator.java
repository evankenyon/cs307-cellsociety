package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;

import java.util.List;

public class TriangularCellCornerLocationGenerator extends CornerLocationGenerator{
    private double height=getVIEW_HEIGHT()/rows;
    private double length=getVIEW_WIDTH()/columns;
    private CornerLocation center;

    public TriangularCellCornerLocationGenerator(int rows, int columns)
    {
        super(rows,columns);
    }

    @Override
    public List<CornerLocation> generateCorners(int i, int j)
    {
        center.setX_pos(j*length+.5*length);
        center.setY_pos(i*height+.5*height);
        if (i%2==0)
        {
            CornerLocation top=new CornerLocation(center.getX_pos(),center.getY_pos()-.5*length);
            CornerLocation bottomLeft=new CornerLocation(j*length,i*height+height);
            CornerLocation bottomRight=new CornerLocation(j*length+length,i*height+height);
            corners.add(top);
            corners.add(bottomRight);
            corners.add(bottomLeft);
        }
        else
        {
            CornerLocation bottom=new CornerLocation(center.getX_pos(),center.getY_pos()+.5*length);
            CornerLocation topLeft=new CornerLocation(j*length,i*height);
            CornerLocation topRight=new CornerLocation(j*length+length,i*height);
            corners.add(bottom);
            corners.add(topLeft);
            corners.add(topRight);
        }
        return corners;
    }
}
