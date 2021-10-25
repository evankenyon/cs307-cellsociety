package cellsociety.cell;


import cellsociety.view.SimulationDisplay;

import cellsociety.location.CornerLocation;

import java.util.*;
import javafx.scene.Node;

import cellsociety.CornerLocationGenerator.RectangleCellCornerLocationGenerator;
import cellsociety.location.CornerLocation;

import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.shape.Rectangle;


public class Cell {

    private int currentState;
    private int futureState;
    private List<Cell> neighbors;
    private Cell[][] cellGrid;
    private List<CornerLocation> corners;
    private int iIndex;
    private int jIndex;
    private HashMap<Integer, Integer> neighborStateMap;
    private CellDisplay myDisplay;
    public static final int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_HEIGHT = 20;

    public Cell(int i, int j, int initialState, int rows, int columns){
        this.iIndex = i;
        this.jIndex = j;
        this.currentState = initialState;
        corners = new RectangleCellCornerLocationGenerator(rows, columns).generateCorners(i, j);
        neighbors = new ArrayList<>();
        myDisplay = new CellDisplay(i * DEFAULT_WIDTH, j * DEFAULT_WIDTH, currentState);
    }



    public Cell(int i, int j, int initialState){
        this(i, j, initialState, 10, 10);
//        this.iIndex = i;
//        this.jIndex = j;
//        this.currentState = initialState;
//        neighbors = new ArrayList<>();
//        myDisplay = new CellDisplay(i * DEFAULT_WIDTH, j * DEFAULT_WIDTH, currentState);
    }


    /**
     * change width of the display
     * @param width will be the width of hte display
     */
    public void setWidth(int width){
        myDisplay.setWidth(width);
    }

    /**
     * chagne the height of the display
     * @param height will be height of the display
     */
    public void setHeight(int height){
        myDisplay.setHeight(height);
    }

    /**
     * change the x coordinate of display
     * @param x
     */
    public void setX(int x){
        myDisplay.setX(x);
    }

    /**
     * change the y coordinate of display
     * @param y
     */
    public void setY(int y){
        myDisplay.setY(y);
    }



    public void updateState(){
        currentState = futureState;
        myDisplay.changeState(currentState);
    }


    public void setFutureState(int state){
        this.futureState = state;
    }

    public int getCurrentState() {
        return currentState;
    }

    public int getFutureState() {return futureState;}

    public List<Cell> getNeighbors() {return neighbors;}

    public List<CornerLocation> getCorners() {
        return corners;
    }

    public void updateNeighbors(Cell potentialNeighbor, int numCornersShared) {
        Set<CornerLocation> sharedCorners = corners.stream()
            .distinct()
            .filter(potentialNeighbor.corners::contains)
            .collect(Collectors.toSet());
        if(sharedCorners.size() >= numCornersShared && !neighbors.contains(potentialNeighbor) && !potentialNeighbor.equals(this)) {
            neighbors.add(potentialNeighbor);
        }
    }

    //Testing purposes
    public void setNeighbors(List<Cell> neighbors) {this.neighbors = neighbors;}

    //FIXME: Not complete
    public void initializeNeighborList(){

        neighbors = new ArrayList<>();


        //FIXME: loop through the cellGrid somehow using corners, then do this next part:
//
//    Cell cell = new Cell(0, 0, 0);
//    if (!neighbors.contains(cell)) {
//        neighbors.add(cell);
//    }
//
        this.createNeighborStateMap();
    }

    public int numOfStateNeighbors(int state){
        if(neighborStateMap.containsKey(state)){
            return neighborStateMap.get(state);
        }
        return 0;
    }

    public void createNeighborStateMap(){
        neighborStateMap = new HashMap<>();
        for(Cell neigbor: neighbors){
           int state = neigbor.getCurrentState();
            neighborStateMap.putIfAbsent(state, 0);
            neighborStateMap.put(state, neighborStateMap.get(state)+1);
        }
    }


    private void setCellGrid(Cell[][] cellGrid){
        this.cellGrid = cellGrid;
    }

    public String getIndex() {
        return iIndex + ", " + jIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return currentState == cell.currentState && futureState == cell.futureState && iIndex == cell.iIndex && jIndex == cell.jIndex && Objects.equals(neighbors, cell.neighbors) && Arrays.equals(cellGrid, cell.cellGrid) && Objects.equals(corners, cell.corners) && Objects.equals(neighborStateMap, cell.neighborStateMap);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentState, futureState, neighbors, corners, iIndex, jIndex, neighborStateMap);
        result = 31 * result + Arrays.hashCode(cellGrid);
        return result;
    }


    public Node getMyDisplay(){
        return myDisplay.getMyDisplay();
    }

}
