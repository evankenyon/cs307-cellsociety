package cellsociety.controller;

import cellsociety.Model.Model;
import cellsociety.Utilities.CSVGenerator;
import cellsociety.Utilities.CSVParser;
import cellsociety.cell.Cell;
import cellsociety.cell.CellDisplay;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javafx.scene.Node;

public class Controller {

  private CSVParser parser;
  private CSVGenerator generator;
  private Model model;
  private MainView mainView;

  public Controller() {
    this.parser = new CSVParser();
    this.generator = new CSVGenerator();
    this.mainView = null;
    this.model = new Model();
  }



  // TODO: actually handle exception
  public void parseFile(File file) throws FileNotFoundException {
    try {
      parser.setFile(file);
      parser.initializeCellMatrix();
      Cell[][] cellGrid = parser.getCellMatrix();
      model.setCellGrid(cellGrid);
    } catch(Exception e){
      e.printStackTrace();
      throw new FileNotFoundException();
    }
  }

  // TODO: actually handle exception
  public void saveFile(String fileName) throws IOException {
    generator.createCSVFile(model.getCellGrid(), fileName);
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
      model.updateCells();
  }

}
