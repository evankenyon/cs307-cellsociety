package cellsociety.controller;

import cellsociety.cell.Cell;
import cellsociety.Model.Model;
import cellsociety.Utilities.CSVGenerator;
import cellsociety.Utilities.CSVParser;
import cellsociety.Utilities.SimGenerator;
import cellsociety.Utilities.SimParser;
import cellsociety.view.MainView;
import cellsociety.view.FileSavePopup;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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

  // TODO: actually handle exception
  public void parseFile(File SimFile) throws FileNotFoundException {
    CSVParser csvParser = new CSVParser();
    SimParser simParser = new SimParser();
    try {
      simParser.setupKeyValuePairs(SimFile);
      csvParser.setFile(new File(String.format("./data/%s", simParser.getSimulationConfig().getProperty("InitialStates"))));
      csvParser.initializeCellMatrix();
      model.setSimulationInfo(simParser.getSimulationConfig());
      simGenerator = new SimGenerator(simParser.getSimulationConfig());
      model.setCellList(csvParser.getAllCells());
      model.setCols(csvParser.getCols());
      model.setRows(csvParser.getRows());
    } catch(Exception e){
      e.printStackTrace();
      throw new FileNotFoundException();
    }
  }

  // TODO: actually handle exception
  public void saveFile(String fileName) throws IOException {
    CSVGenerator csvGenerator = new CSVGenerator();
    csvGenerator.createCSVFile(model.getCellGrid(), fileName);
//    simGenerator.updateReplaceableSimInfo(updatedSimInfo);
    simGenerator.createSimFile(fileName);
  }

  // TODO: actually handle exception
  public void saveFile(String fileName, Map<String, String> propertyToValue) throws IOException {
    CSVGenerator csvGenerator = new CSVGenerator();
    csvGenerator.createCSVFile(model.getCellGrid(), propertyToValue.get(FileSavePopup.INITIAL_STATES));
//    simGenerator.updateReplaceableSimInfo(updatedSimInfo);

    simGenerator.createSimFile(propertyToValue);
  }



  public void setModel(Model model) {
    this.model = model;
  }

  /**
   *  SHUOLD DELETE THIS METHOD
   * get a list of the display nodes for each cell
   * @return a list of the display node for each cell
   */
  public List<Node> getCellDisplays(){
    return model.getCellDisplays();
  }

  /**
   * get a list of the cells in the simulation
   * @return a list of each cell
   */
  public List<Cell> getCells(){
    return model.getCells();
  }

  /**
   * perform the next step in the simulation
   */
  public void step() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    model.findNextStateForEachCell();
    model.updateModel();
  }

  /**
   * get the shape of the grid for this simulation
   * @return model.getGridShape()
   */
  public int[] getGridShape(){
    return model.getGridShape();
  }

  /**
   * get the simulation type, e.g. "GameOfLife" or "Percolation"
   * @return the the model's string simulationType
   */
  public String getSimulationType(){
    return model.getSimulationType();
  }

}
