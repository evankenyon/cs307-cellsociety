package cellsociety.controller;

import cellsociety.Model.Model;
import cellsociety.Utilities.CSVGenerator;
import cellsociety.Utilities.CSVParser;
import cellsociety.Utilities.SimGenerator;
import cellsociety.Utilities.SimParser;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;

public class Controller {
  private Model model;
  private MainView mainView;
  private SimGenerator simGenerator;

  public Controller() {
    this.mainView = null;
    this.model = new Model();
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
      model.setCellGrid(csvParser.getAllCells(), csvParser.getRows(), csvParser.getCols());
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

  public void setMainView(MainView mainView) {
    this.mainView = mainView;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public List<Node> getCellDisplays(){
    return model.getCellDisplays();
  }

  public void step(){
    model.findNextStateForEachCell();
    model.updateModel();
  }

}
