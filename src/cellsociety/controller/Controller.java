package cellsociety.controller;

import cellsociety.Model.Model;
import cellsociety.Utilities.CSVGenerator;
import cellsociety.Utilities.CSVParser;
import cellsociety.cell.Cell;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {

  private CSVParser parser;
  private CSVGenerator generator;
  private Model model;
  private MainView mainView;

  public Controller() {
    this.parser = new CSVParser();
    this.generator = new CSVGenerator();
    this.mainView = null;
    this.model = null;
  }

  // TODO: actually handle exception
  public void parseFile(File file) throws FileNotFoundException {
    parser.setFile(file);
    parser.initializeCellMatrix();
    Cell[][] cellGrid = parser.getCellMatrix();
    model.setCellGrid(cellGrid);
  }

  // TODO: actually handle exception
  public void saveFile() throws IOException {
    generator.createCSVFile(model.getCellGrid());
  }

  public void setMainView(MainView mainView) {
    this.mainView = mainView;
  }

  public void setModel(Model model) {
    this.model = model;
  }

}
