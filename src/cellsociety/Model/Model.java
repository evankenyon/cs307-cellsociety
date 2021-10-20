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
    }

    public Cell[][] getCellGrid() {
        return cellGrid;
    }


    public void findNextStateForEachCell(){
        for(int row = 0; row <cellGrid.length; row++){
            for(int col = 0; col<cellGrid[0].length; col++){
               GameOfLifeRules r= new GameOfLifeRules(cellGrid[row][col]);
               r.setState();
            }
        }

    } //loop through each cell, set its future state
    public void updateCells(){
        for(int row = 0; row <cellGrid.length; row++){
            for(int col = 0; col<cellGrid[0].length; col++){
                cellGrid[row][col].updateState();
            }
        }
    } //loop through each cell, set its current state to future state, calls updateCurrentStateMethod

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
