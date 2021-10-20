package cellsociety.Utilities;

import cellsociety.cell.Cell;

import cellsociety.view.SimulationDisplay;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class CSVParser {
    private File file;
    private int rows;
    private int cols;
    private int [][] integer2DArray;
    private Cell[][] cellMatrix;

    public void setFile(File file) {
        this.file = file;
    }

    public void makeInteger2DArray() throws FileNotFoundException{
        Scanner scanner;
        scanner = new Scanner(file);
        if(scanner.hasNextLine()){
            String dimensionInformation = scanner.nextLine();
            dimensionInformation = dimensionInformation.strip();
            String[] dimensions = dimensionInformation.split(",");
            //FIXME: Need to throw an exception when it isnt an integer
             rows = Integer.valueOf(dimensions[0]);
             cols = Integer.valueOf(dimensions[1]);
        }
        int[][] integer2DArray = new int[rows][cols];
        int currentXIndex = 0;
        while (scanner.hasNextLine()) {
            String xIndexInfo = scanner.nextLine();
            xIndexInfo = xIndexInfo.strip();
            integer2DArray[currentXIndex] =  Arrays.stream(xIndexInfo.split(",")).mapToInt(Integer::parseInt).toArray();
            currentXIndex++;
        }

        scanner.close();
        this.integer2DArray =  integer2DArray;

    }


    public void initializeCellMatrix() throws FileNotFoundException {
        this.makeInteger2DArray();
        Cell[][] cellMatrix = new Cell[rows][cols];
        for(int row = 0; row <integer2DArray.length; row++){
            for(int col = 0; col<integer2DArray[0].length; col++){
                Cell newCell = new Cell(row, col, integer2DArray[row][col],
                    SimulationDisplay.DEFAULT_WIDTH / cols, SimulationDisplay.DEFAULT_WIDTH / rows);
                cellMatrix[row][col] = newCell;
            }
        }
        this.cellMatrix = cellMatrix;
    }

    public Cell[][] getCellMatrix() {
        return cellMatrix;
    }
}
