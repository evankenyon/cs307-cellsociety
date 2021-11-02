package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;
import java.util.List;

public class HexagonalCellCornerLocationGenerator extends CornerLocationGenerator{
    private double height=getVIEW_HEIGHT()/rows;
    private double size=height/2;
    private double width =size*Math.sqrt(3);
    private CornerLocation center=new CornerLocation(0,0);
    private int ROUND_VALUE=100;

    public HexagonalCellCornerLocationGenerator(int rows, int columns)
    {
        super(rows,columns);
    }

    /**
     *
     * @param i
     * @param j
     * @return a list of the corners for each individual hexagon based on its index
     * and the size of the simulation window
     */
    @Override
    public List<CornerLocation> generateCorners(int i, int j)
    {
        corners.clear();
        center.setY_pos(i*(height-.5* width *Math.cos(Math.toRadians(30)))+size);
        if (i%2==0)
        {
            center.setX_pos(j*width+.5*width);
        }
        else
        {
            center.setX_pos(j*width+width);
        }

        CornerLocation top=new CornerLocation(center.getX_pos(), center.getY_pos()-size);
        CornerLocation bottom=new CornerLocation(center.getX_pos(), center.getY_pos()+size);
        CornerLocation bottomLeft=new CornerLocation(center.getX_pos()-.5*width,center.getY_pos()+size-.5* width *Math.cos(Math.toRadians(30)));
        CornerLocation bottomRight=new CornerLocation(center.getX_pos()+.5*width,center.getY_pos()+size -.5* width *Math.cos(Math.toRadians(30)));
        CornerLocation topLeft=new CornerLocation(center.getX_pos()-.5*width,center.getY_pos()-size+.5* width *Math.cos(Math.toRadians(30)));
        CornerLocation topRight=new CornerLocation(center.getX_pos()+.5*width,center.getY_pos()-size+.5* width *Math.cos(Math.toRadians(30)));
        corners.add(top);
        corners.add(topLeft);
        corners.add(bottomLeft);
        corners.add(bottom);
        corners.add(bottomRight);
        corners.add(topRight);
        return corners;
    }
}
