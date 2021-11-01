package cellsociety.Utilities;

import cellsociety.cell.Cell;

import cellsociety.cell.IllegalCellStateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CSVParser {
    private static final String DEFAULT_RESOURCE_PACKAGE =
        CSVParser.class.getPackageName() + ".resources.";
    private static final String VALID_STATES_FILENAME = "ValidStates";

    private File file;
    private int rows;
    private int cols;
    private int [][] integer2DArray;
    private List<Integer> cellStates;

    public CSVParser() {
        cellStates = new ArrayList<>();
    }

    public void setFile(File file) throws InputMismatchException {
        if(!file.getName().endsWith(".csv")) {
            throw new InputMismatchException();
        }
        this.file = file;
    }

    private void makeInteger2DArray() throws FileNotFoundException{
        Scanner scanner;
        scanner = new Scanner(file);
        if(scanner.hasNextLine()){
            String dimensionInformation = scanner.nextLine();
            dimensionInformation = dimensionInformation.strip();
            String[] dimensions = dimensionInformation.split(",");
            //FIXME: Need to throw an exception when it isnt an integer
             rows = Integer.valueOf(dimensions[1]);
             cols = Integer.valueOf(dimensions[0]);
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


    public void initializeCellMatrix(String simType)
        throws FileNotFoundException, IllegalCellStateException {
        this.makeInteger2DArray();
        List<Integer> validStatesList = getValidStatesList(simType);
        for(int row = 0; row < integer2DArray.length; row++){
            for(int col = 0; col<integer2DArray[0].length; col++){
                if (!validStatesList.contains(integer2DArray[row][col])) {
                    throw new IllegalCellStateException();
                }
                cellStates.add(integer2DArray[row][col]);
            }
        }
    }

    private List<Integer> getValidStatesList(String simType) {
        ResourceBundle validStatesBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + VALID_STATES_FILENAME);
        if(validStatesBundle.containsKey(simType)) {
            return extractIntList(validStatesBundle.getString(simType));
        }
        return extractIntList(validStatesBundle.getString("Default"));
    }

    private List<Integer> extractIntList(String commaArr) {
        String[] arr = commaArr.split(",");
        List<Integer> intList = new ArrayList<>();
        for(String element : arr) {
            intList.add(Integer.parseInt(element));
        }
        return intList;
    }

    public List<Integer> getCellStates() {
        return cellStates;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
