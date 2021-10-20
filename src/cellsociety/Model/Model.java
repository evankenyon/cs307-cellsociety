package cellsociety.Model;

import cellsociety.cell.Cell;
import cellsociety.Utilities.CSVParser;
import cellsociety.Rule.GameOfLifeRules;
import javafx.scene.Node;
import java.util.List;
import java.util.ArrayList;

import java.io.File;

public class Model {

    Cell [][] cellGrid;


    public Model(){
        cellGrid = null;
    }

    public void setCellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
        updateAllNeighbors();
    }

    public Cell[][] getCellGrid() {
        return cellGrid;
    }

    private void updateAllNeighbors() {
        for (int row = 0; row < cellGrid.length; row++) {
            for (int col = 0; col < cellGrid[0].length; col++) {
                Cell cell = cellGrid[row][col];
                for (int checkAgainstRow = 0; checkAgainstRow < cellGrid.length; checkAgainstRow++) {
                    for (int checkAgainstCol = 0; checkAgainstCol < cellGrid[0].length; checkAgainstCol++) {
                        cell.updateNeighbors(cellGrid[checkAgainstRow][checkAgainstCol]);
                    }
                }
            }
        }
    }

    /**
     * find what the next state should be for each cell
     */
    public void findNextStateForEachCell(){
        for(int row = 0; row <cellGrid.length; row++){
            for(int col = 0; col<cellGrid[0].length; col++){
               GameOfLifeRules r = new GameOfLifeRules(cellGrid[row][col]);
               r.setState();
            }
        }

    }

    /**
     * loop through each cell, set its future state
     */
    public void updateCells(){
        for(int row = 0; row <cellGrid.length; row++){
            for(int col = 0; col<cellGrid[0].length; col++){
                cellGrid[row][col].updateState();
            }
        }
    } //loop through each cell, set its current state to future state, calls updateCurrentStateMethod

    /**
     * get a list of all the nodes to go on screen, representing displays of each cell
     * @return a list of nodes displaying the cells
     */
    public List<Node> getCellDisplays(){
        List<Node> nodeList = new ArrayList<>();
        for(int row = 0; row <cellGrid.length; row++){
            for(int col = 0; col<cellGrid[0].length; col++){
                nodeList.add(cellGrid[row][col].getMyDisplay());
            }
        }
        return nodeList;
    }

}
