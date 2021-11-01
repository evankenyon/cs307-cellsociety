package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;
import java.util.List;

public class HexagonalCellCornerLocationGenerator extends CornerLocationGenerator{
    private double height=getVIEW_HEIGHT()/rows;
    private double size=height/2;
    private double width =size*Math.sqrt(3);
    private CornerLocation center;

    public HexagonalCellCornerLocationGenerator(int rows, int columns)
    {
        super(rows,columns);
    }

    @Override
    public List<CornerLocation> generateCorners(int i, int j)
    {
        center.setY_pos(i*height+size);
        if (j%2==0)
        {
            center.setX_pos(j*width+.5*width);
        }
        else
        {
            center.setX_pos(j*width+width);
        }

        CornerLocation top=new CornerLocation(center.getX_pos(), center.getY_pos()-size);
        CornerLocation bottom=new CornerLocation(center.getX_pos(), center.getY_pos()+size);
        CornerLocation bottomLeft=new CornerLocation((j)*width,center.getY_pos()+size-.5* width *Math.cos(Math.toRadians(30)));
        CornerLocation bottomRight=new CornerLocation((j+1)*width,center.getY_pos()+size -.5* width *Math.cos(Math.toRadians(30)));
        CornerLocation topLeft=new CornerLocation((j)* width,center.getY_pos()-size+.5* width *Math.cos(Math.toRadians(30)));
        CornerLocation topRight=new CornerLocation((j+1)* width,center.getY_pos()-size+.5* width *Math.cos(Math.toRadians(30)));
        corners.add(top);
        corners.add(topLeft);
        corners.add(bottomLeft);
        corners.add(bottom);
        corners.add(bottomRight);
        corners.add(topRight);
        return corners;
    }
}
