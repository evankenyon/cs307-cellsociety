package cellsociety.controller;

import cellsociety.Model.Model;
import cellsociety.Utilities.CSVParser;
import cellsociety.cell.Cell;
import cellsociety.view.MainView;
import java.io.File;
import java.io.FileNotFoundException;

public class Controller {

  private CSVParser parser;
  private Model model;
  private MainView mainView;

  public Controller() {
    this.parser = new CSVParser();
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

  public void setMainView(MainView mainView) {
    this.mainView = mainView;
  }

  public void setModel(Model model) {
    this.model = model;
  }

}
