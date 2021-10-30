package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;
import java.util.List;

public class HexagonalCellCornerLocationGenerator extends CornerLocationGenerator{
    private double height=getVIEW_HEIGHT()/rows;
    private double length=getVIEW_WIDTH()/columns;
    private CornerLocation center;

    public HexagonalCellCornerLocationGenerator(int rows, int columns)
    {
        super(rows,columns);
    }

    @Override
    public List<CornerLocation> generateCorners(int i, int j)
    {
        center.setX_pos(j*length+.5*length);
        center.setY_pos(i*height+.5*height);
        CornerLocation top=new CornerLocation(center.getX_pos(), center.getY_pos()-.5*height);
        CornerLocation bottom=new CornerLocation(center.getX_pos(), center.getY_pos()+.5*height);
        CornerLocation bottomLeft=new CornerLocation((j)*length,center.getY_pos()+.5*length-.5*length*Math.cos(Math.toRadians(30)));
        CornerLocation bottomRight=new CornerLocation((j+1)*length,center.getY_pos()+.5*length-.5*length*Math.cos(Math.toRadians(30)));
        CornerLocation topLeft=new CornerLocation((j)*length,i*length+.5*length*Math.cos(Math.toRadians(30)));
        CornerLocation topRight=new CornerLocation((j+1)*length,i*length+.5*length*Math.cos(Math.toRadians(30)));
        corners.add(top);
        corners.add(bottom);
        corners.add(bottomLeft);
        corners.add(bottomRight);
        corners.add(topLeft);
        corners.add(topRight);
        return corners;
    }
}
