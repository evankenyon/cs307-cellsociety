package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class CornerLocationGenerator {
    protected final double VIEW_WIDTH=800;
    protected final double VIEW_HEIGHT=800;

    protected List<CornerLocation> corners=new ArrayList<>();
    protected int rows;
    protected int columns;

    public CornerLocationGenerator()
    {
        this.rows=0;
        this.columns=0;
    }
    public CornerLocationGenerator(int rows, int columns)
    {
        this.rows=rows;
        this.columns=columns;
    }

    public List<CornerLocation> generateCorners(int i, int j) {return corners;}
}
