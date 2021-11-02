package cellsociety.view;


import cellsociety.Utilities.CSVParser.IllegalRowSizeException;
import cellsociety.Utilities.CSVParser.InvalidDimensionException;
import cellsociety.cell.IllegalCellStateException;
import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.ViewResourceHandler;
import cellsociety.controller.Controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.InputMismatchException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;

import cellsociety.cell.Cell;
import cellsociety.cell.CellDisplay;

import cellsociety.CornerLocationGenerator.RectangleCellCornerLocationGenerator;
import cellsociety.CornerLocationGenerator.CornerLocationGenerator;
import cellsociety.CornerLocationGenerator.HexagonalCellCornerLocationGenerator;
import cellsociety.CornerLocationGenerator.TriangularCellCornerLocationGenerator;
import cellsociety.location.CornerLocation;


import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


/**
 * Objects of this class represent the display for a single simulation (I say single since there can be multiple
 * simulations on the screen at once).
 * @author Keith Cressman
 */
public class SimulationDisplay extends ChangeableDisplay{

  private double framesPerSecond = 1;
  private double secondDelay = 1.0 / framesPerSecond;
  private Controller myController;
  protected Timeline myAnimation;
  private boolean paused;
  private Button pauseButton, resumeButton;
  private TextField fileNameField;
  private ViewResourceHandler myViewResourceHandler;
  private List<CellDisplay> allCellDisplays;
  private Node myNode;
  private CellGridDisplay myCellGridDisplay;
  private HistogramDisplay myHistogram;
  private InfoDisplay myInfoDisplay;
  private SettingsDisplay mySettingsDisplay;




  public SimulationDisplay(){
    this(new LanguageResourceHandler());
  }

  public SimulationDisplay(LanguageResourceHandler l){
    super(l);
    myController = new Controller();
    myViewResourceHandler = new ViewResourceHandler();
  }


  /**
   * create the display (i.e. a grid with each cell) to put on the MainView
   * @return a Node to display on the MainView
   */
  public Node makeDisplay(File SimFile) {
    try {
      myController.parseFile(SimFile);
    }catch (IOException e ){
      displayErrorMessage(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.BAD_FILE_KEY));
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      // TODO: move to props file
      displayErrorMessage("Reflection error occurred in backend model, please try restarting the"
          + "program");
    } catch (IllegalCellStateException e) {
      // TODO: move to props file
      displayErrorMessage("Illegal cell state was found, please make sure you are not using any values"
          + "that are not 0 or 1 for GameOfLife or not 0, 1, or 2 for other simulations");
    } catch (InputMismatchException e) {
      // TODO: move to props file
      displayErrorMessage("A file that was not of .csv type was uploaded");
    } catch (IllegalArgumentException e) {
      // TODO: move to props file
      displayErrorMessage("A cell was initialized to be outside of the grid's bounds, please try restarting the program");
    } catch (InvalidDimensionException e) {
      e.printStackTrace();
    } catch (IllegalRowSizeException e) {
      e.printStackTrace();
    }
    VBox root = new VBox();
    root.getChildren().add(makeAllDisplays());
    mySettingsDisplay = new SettingsDisplay(myController, myLanguageResourceHandler, this);
    mySubDisplays.add(mySettingsDisplay);
    root.getChildren().add(mySettingsDisplay.createSettingsDisplay());
    myNode = root;
    return root;
  }

  private Node makeAllDisplays(){
    //return a node with the grid, histogram, and other option
    HBox simulationsBox = new HBox();
    simulationsBox.setSpacing(20); //change magic valeu
    //simulationsBox.getChildren().add(makeCellsAndBackground());
    simulationsBox.getChildren().add(makeCellGridDisplay());
    simulationsBox.getChildren().add(makeHistogram());
    simulationsBox.getChildren().add(makeInfoDisplay());
    return simulationsBox;
  }

  private Node makeCellGridDisplay(){
    //make a node containing the grid
    myCellGridDisplay = new CellGridDisplay(myController);
    mySubDisplays.add(myCellGridDisplay);
    return myCellGridDisplay.createGridDisplay();
  }


  private Node makeHistogram(){
    //create the histogram to add it onto the main node
    myHistogram = new HistogramDisplay(myController.getNumCells(), getNumOfEachState(), myLanguageResourceHandler);
    mySubDisplays.add(myHistogram);
    return myHistogram.createHistogramDisplay();
  }

  private Node makeInfoDisplay(){
    //make the node with the info display to add it onto the main node
    myInfoDisplay = new InfoDisplay(getNumOfEachState(), myLanguageResourceHandler);
    mySubDisplays.add(myInfoDisplay);
    return myInfoDisplay.createInfoDisplay();
  }

  /**
   * Create/return a map that maps each state to the number of cells in that state
   * This method should probably go in model, I don't like having it in here. It's kinda clunky. -Keith
   * @return a map used to find how many cells in each state
   */
  private Map<Integer, Integer> getNumOfEachState(){
    Map<Integer, Integer> stateToCount = new HashMap<>();
    int numStates = myCellGridDisplay.getAllCellDisplays().get(0).getStateColors().length; //dumb way to find num states but it work
    for (int i = 0; i < numStates; i++){
      stateToCount.put(i, 0);
    }
    for (CellDisplay c : myCellGridDisplay.getAllCellDisplays()){
      int state = c.getState();
      stateToCount.putIfAbsent(state, 0);
      stateToCount.put(state, 1 + stateToCount.get(state));
    }
    return stateToCount;
  }


  /**
   * perform the next step in the simulation
   */
  public void step() {
    try {
      myController.step();
      myHistogram.setNumOfEachType(getNumOfEachState());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      // TODO: move to props file
      displayErrorMessage("Reflection error occurred in backend model, please try restarting the"
          + "program");
    } catch (IllegalCellStateException e) {
      // TODO: move to props file
      displayErrorMessage("A cell was set to an illegal state in the backend model, please try restarting the program");
    }
    myInfoDisplay.setNumOfEachType((getNumOfEachState()));

  }


  /**
   * returns the second delay of the timeline. Used for testing
   * @return the second delay of the animation
   */
  public double getAnimationSpeed(){
    return mySettingsDisplay.getAnimationSpeed();
  }

  /**
   * get a list of all the cell displays in the simulation
   * @return allCellDisplays
   */
  public List<CellDisplay> getAllCellDisplays(){
    return myCellGridDisplay.getAllCellDisplays();
  }

  /**
   * change the shape of the cells in the grid
   * @param s is the new shape, like "Triangle" or "Hexagon"
   */
  public void changeCellShapes(String s){
    myCellGridDisplay.changeCellShapes(s);
  }

  /**
   * get the node containing all the display stuff for this simulation.
   * This will allow us to remove it from the MainView
   * @return myNode
   */
  public Node getMyNode(){
    return myNode;
  }


  /**
   * show/hide the info display
   * @param visible should be true to show the info display, false to hide it
   */
  public void showInfoDisplay(boolean visible){
    myInfoDisplay.setVisible(visible);
  }


  /**
   * show/hide the cell grid
   * @param visible should be true to show the grid, false to hide it
   */
  public void showGrid(boolean visible){
    myCellGridDisplay.setVisible(visible);
  }

  /**
   * show/hide the histogram
   * @param visible should be true to show the histogram, false to hide it
   */
  public void showHistogram(boolean visible){
    myHistogram.setVisible(visible);
  }


}
