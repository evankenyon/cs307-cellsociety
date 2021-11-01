package cellsociety.CornerLocationGenerator;

import cellsociety.location.CornerLocation;
import cellsociety.resourceHandlers.ViewResourceHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class CornerLocationGenerator {

    private ViewResourceHandler myViewResourceHandler;
    private double VIEW_WIDTH;
    private double VIEW_HEIGHT;

    protected List<CornerLocation> corners=new ArrayList<>();
    protected int rows;
    protected int columns;

    public CornerLocationGenerator()
    {
        this.rows=0;
        this.columns=0;
        myViewResourceHandler=new ViewResourceHandler();
        VIEW_WIDTH=myViewResourceHandler.simulationWidth();
        VIEW_HEIGHT=myViewResourceHandler.simulationHeight();
    }
    public CornerLocationGenerator(int rows, int columns)
    {
        this.rows=rows;
        this.columns=columns;
        myViewResourceHandler=new ViewResourceHandler();
        VIEW_WIDTH=myViewResourceHandler.simulationWidth();
        VIEW_HEIGHT=myViewResourceHandler.simulationHeight();
    }

    public abstract List<CornerLocation> generateCorners(int i, int j);

    public double getVIEW_HEIGHT() {
        return VIEW_HEIGHT;
    }

    public double getVIEW_WIDTH() {
        return VIEW_WIDTH;
    }
}
