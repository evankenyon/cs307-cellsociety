package cellsociety.Model;

import cellsociety.CellParts.Cell;
import cellsociety.Utilities.CSVParser;

public class Model {

    Cell [][] cellGrid;
    CSVParser parser;



//                r.setState();

    public void findNextStateForEsachCell(){
        for(int row = 0; row <cellGrid.length; row++){
            for(int col = 0; col<cellGrid[0].length; col++){
//               Rule r=new Rule(cellGrid[row][col]);
//                r.setState();
            }
        }

    } //loop through each cell, set its future state
    public void updateCells(){
        for(int row = 0; row <cellGrid.length; row++){
            for(int col = 0; col<cellGrid[0].length; col++){
                //cellGrid[row][col].updateState();
//
                cellGrid[row][col].updateState();
            }
        }
    } //loop through each cell, set its current state to future state, calls updateCurrentStateMethod

}
