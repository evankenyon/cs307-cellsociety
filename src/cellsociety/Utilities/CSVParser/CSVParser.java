package cellsociety.Utilities.CSVParser;

import cellsociety.cell.IllegalCellStateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;

public abstract class CSVParser {
    private static final String DEFAULT_RESOURCE_PACKAGE =
        CSVParser.class.getPackageName() + ".resources.";
    private static final String VALID_STATES_FILENAME = "ValidStates";
    private File file;
    private int rows;
    private int cols;

    public File getFile() {
        return file;
    }

    public void setFile(File file) throws InputMismatchException {
        if(!file.getName().endsWith(".csv")) {
            throw new InputMismatchException();
        }
        this.file = file;
    }

    public abstract List<Integer> getCellStates(String simType) throws FileNotFoundException, IllegalCellStateException, IllegalRowSizeException, InvalidDimensionException;

    public List<Integer> getValidStatesList(String simType) {
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

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}
