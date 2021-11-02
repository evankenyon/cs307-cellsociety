package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;

import java.util.List;

public class TriangularCellCornerLocationGenerator extends CornerLocationGenerator{
    private double height=getVIEW_HEIGHT()/rows;
    private double length=getVIEW_WIDTH()/columns;
    private CornerLocation center=new CornerLocation(0,0);

    public TriangularCellCornerLocationGenerator(int rows, int columns)
    {
        super(rows,columns);
    }

    /**
     *
     * @param i
     * @param j
     * @return a list of the corners for each individual triangle based on its index
     * and the size of the simulation window
     */
    @Override
    public List<CornerLocation> generateCorners(int i, int j)
    {
        corners.clear();
        if (i%2==0)
        {
            center.setX_pos(.5*j*length+.5*length);
        }
        else
        {
            center.setX_pos(.5*j*length+length);
        }

        center.setY_pos(i*height+.5*height);
        if (j%2==0)
        {
            CornerLocation top=new CornerLocation(center.getX_pos(),center.getY_pos()-.5*height);
            CornerLocation bottomLeft=new CornerLocation(center.getX_pos()-.5*length,center.getY_pos()+.5*height);
            CornerLocation bottomRight=new CornerLocation(center.getX_pos()+.5*length,center.getY_pos()+.5*height);
            corners.add(top);
            corners.add(bottomRight);
            corners.add(bottomLeft);
        }
        else
        {
            CornerLocation bottom=new CornerLocation(center.getX_pos(),center.getY_pos()+.5*height);
            CornerLocation topLeft=new CornerLocation(center.getX_pos()-.5*length,center.getY_pos()-.5*height);
            CornerLocation topRight=new CornerLocation(center.getX_pos()+.5*length,center.getY_pos()-.5*height);
            corners.add(bottom);
            corners.add(topLeft);
            corners.add(topRight);
        }
        return corners;
    }
}
