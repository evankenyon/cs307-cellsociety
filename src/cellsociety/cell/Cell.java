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
    public static final int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_HEIGHT = 20;

    private int currentState;
    private int futureState;
    private List<Cell> neighbors;
    private Cell[][] cellGrid;
    private List<CornerLocation> corners;
    private int iIndex;
    private int jIndex;
    private Map<Integer, List<Cell>> neighborCellStateMap;
    private CellDisplay myDisplay;
    private boolean shouldMove;
    private int chrononCounter=0;
    private int energy=5;

    public Cell(int i, int j, int initialState, int rows, int columns){
        this.iIndex = i;
        this.jIndex = j;
        this.currentState = initialState;
        corners = new RectangleCellCornerLocationGenerator(rows, columns).generateCorners(i, j);
        neighbors = new ArrayList<>();
        myDisplay = new CellDisplay(j * DEFAULT_WIDTH, i * DEFAULT_WIDTH, currentState);
        neighborCellStateMap = new HashMap<>();
        shouldMove = false;
        chrononCounter = 0;
        energy = 5;
    }



    public Cell(int i, int j, int initialState){
        this(i, j, initialState, 10, 10);
    }

    /**
     * set what myDisplay refers to
     * @param disp will become myDisplay
     */
    public void setDisplay(CellDisplay disp){
        myDisplay = disp;
    }

    public int getjIndex() {
        return jIndex;
    }

    public int getiIndex() {
        return iIndex;
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

    public void setShouldMove(boolean shouldMove) {this.shouldMove = shouldMove;}

    public boolean isShouldMove() {return shouldMove;}

    public void setChrononCounter(int chrononCounter) {this.chrononCounter = chrononCounter;}

    public int getChrononCounter() {return chrononCounter;}

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
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

    public int numOfStateNeighbors(int state){
        if(neighborCellStateMap.containsKey(state)){
            return neighborCellStateMap.get(state).size();
        }
        return 0;
    }

    public void updateCellNeighborStateMap(){
        neighborCellStateMap = new HashMap<>();
        for(Cell neighbor: neighbors){
            int state = neighbor.getCurrentState();
            neighborCellStateMap.putIfAbsent(state, new ArrayList<Cell>());
            neighborCellStateMap.get(state).add(neighbor);
        }
    }

    public int getNumNeighbors() {
        return neighbors.size();
    }

    // TODO: Remove
    public void setNeighbors(List<Cell> neighbors) {this.neighbors = neighbors;}


    public Cell getNeighborOfState(int state, int num) {
        return neighborCellStateMap.get(state).get(num);
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
        return currentState == cell.currentState && futureState == cell.futureState && iIndex == cell.iIndex && jIndex == cell.jIndex && Objects.equals(neighbors, cell.neighbors) && Arrays.equals(cellGrid, cell.cellGrid) && Objects.equals(corners, cell.corners) && Objects.equals(neighborCellStateMap, cell.neighborCellStateMap);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentState, futureState, neighbors, corners, iIndex, jIndex, neighborCellStateMap);
        result = 31 * result + Arrays.hashCode(cellGrid);
        return result;
    }


    public Node getMyDisplay(){
        return myDisplay.getMyDisplay();
    }

}
