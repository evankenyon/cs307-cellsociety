package cellsociety.controller;

import cellsociety.Model.Model;
import cellsociety.Utilities.CSVGenerator;
import cellsociety.Utilities.CSVParser;
import cellsociety.Utilities.SimParser;
import cellsociety.cell.Cell;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javafx.scene.Node;

public class Controller {

  private CSVParser csvParser;
  private CSVGenerator csvGenerator;
  private SimParser simParser;
  private Model model;
  private MainView mainView;

  public Controller() {
    this.csvParser = new CSVParser();
    this.csvGenerator = new CSVGenerator();
    this.simParser = new SimParser();
    this.mainView = null;
    this.model = new Model();
  }



  // TODO: actually handle exception
  public void parseFile(File file) throws FileNotFoundException {
    try {
      simParser.setupKeyValuePairs(file);
      csvParser.setFile(file);
      csvParser.initializeCellMatrix();
      Cell[][] cellGrid = csvParser.getCellMatrix();
      model.setSimulationInfo(simParser.getSimulationConfig());
      model.setCellGrid(cellGrid);
    } catch(Exception e){
      e.printStackTrace();
      throw new FileNotFoundException();
    }
  }

  // TODO: actually handle exception
  public void saveFile(String fileName) throws IOException {
    csvGenerator.createCSVFile(model.getCellGrid(), fileName);
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
