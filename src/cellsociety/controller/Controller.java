package cellsociety.controller;

import cellsociety.Rule.RulesInterface;
import cellsociety.Utilities.CSVParser.DefaultCSVParser;
import cellsociety.Utilities.CSVParser.IllegalRowSizeException;
import cellsociety.Utilities.CSVParser.InvalidDimensionException;
import cellsociety.cell.Cell;
import cellsociety.Model.Model;
import cellsociety.Utilities.CSVGenerator;
import cellsociety.Utilities.CSVParser.CSVParser;
import cellsociety.Utilities.SimGenerator;
import cellsociety.Utilities.SimParser;
import cellsociety.cell.IllegalCellStateException;
import com.opencsv.ICSVParser;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.scene.Node;

public class Controller {
  private static final String DEFAULT_RESOURCE_PACKAGE =
      SimParser.class.getPackageName() + ".resources.";
  private static final String OPTIONAL_KEYS_FILENAME = "OptionalKeys";

  private Model model;
  private SimGenerator simGenerator;
  private ResourceBundle optionalKeys;

  public Controller() {
    this.model = new Model();
    optionalKeys = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + OPTIONAL_KEYS_FILENAME);
  }

  public void parseFile(File SimFile)
      throws NullPointerException, IOException, InputMismatchException, IllegalArgumentException, InvalidDimensionException, IllegalRowSizeException, IllegalCellStateException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    CSVParser csvParser = null;
    SimParser simParser = new SimParser();
    simParser.setupKeyValuePairs(SimFile);
    try {
      csvParser = (CSVParser) Class.forName(
              String.format("%s.%sCSVParser", CSVParser.class.getPackage().getName(), simParser.getSimulationConfig().getProperty("CSVType")))
          .getConstructor()
          .newInstance();
    } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      csvParser = new DefaultCSVParser();
    }
    csvParser.setFile(new File(String.format("./data/%s", simParser.getSimulationConfig().getProperty("InitialStates"))));
    model.setSimulationInfo(simParser.getSimulationConfig());
    simGenerator = new SimGenerator(simParser.getSimulationConfig());
    model.setupCells(csvParser.getCellStates(simParser.getSimulationConfig().getProperty("Type")), csvParser.getRows(), csvParser.getCols());
  }

  public void addCellObserver(int row, int col, Consumer<Integer> observer) {
    model.getCell(row, col).addObserver(observer);
  }

  public void saveFile(String fileName, Map<String, String> propertyToValue) throws IOException {
    simGenerator.createSimFile(fileName, propertyToValue);
    CSVGenerator csvGenerator = new CSVGenerator();
    csvGenerator.createCSVFile(model, fileName, getSimulationType());
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public void setCellState(int row, int col, int state) {
    Cell cellToUpdate = model.getCell(row, col);
    cellToUpdate.setFutureState(state);
    cellToUpdate.updateState();
  }

  public Cell getCell(int i, int j) {
    return model.getCell(i, j);
  }

  /**
   * perform the next step in the simulation
   */
  public void step()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException, IllegalCellStateException, InputMismatchException {
    model.findNextStateForEachCell();
    model.updateModel();
  }

  /**
   * get the shape of the grid for this simulation
   * @return model.getGridShape()
   */
  public int[] getGridShape(){
    return new int[]{model.getRows(), model.getCols()};
  }

  /**
   * get the number of cells in the simulation
   * @return number of cells
   */
  public int getNumCells(){
    return model.getRows() * model.getCols();
  }

  /**
   * get the simulation type, e.g. "GameOfLife" or "Percolation"
   * @return the the model's string simulationType
   */
  public String getSimulationType(){
    return model.getSimulationType();
  }

  /**
   * change the neighbor arrangement. Could also pass in a lambda/method to this, or you can use the string
   * as a key in resourceHandlers.NeighborArrangements.Properties to get something
   * @param newArrangement is a String like "Complete" or "Cardinal"
   */
  public void changeNeighborArrangement(String newArrangement)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    model.updateNeighborArrangement(newArrangement);
  }

  /**
   * change the edge policy. Could also pass in a lambda/method to this, or you can use the string
   * as a key in resourceHandlers.EdgePolicies.Properties to get something
   * @param newPolicy is a String like "Toroidal" or "Finite"
   */
  public void changeEdgePolicy(String newPolicy){

  }

  public void changeShape(String shape) {
    model.changeShapeOfCells(shape);
  }

}
