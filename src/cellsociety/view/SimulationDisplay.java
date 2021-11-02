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



  public SimulationDisplay(){
    this(new LanguageResourceHandler());
  }

  public SimulationDisplay(LanguageResourceHandler l){
    super(l);
    myController = new Controller();
    myViewResourceHandler = new ViewResourceHandler();
  }

  protected void setUpAnimation(){
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(secondDelay), e -> step()));
    myAnimation.play();
    paused = false;
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
    root.getChildren().add(makeControls());
    setUpAnimation();
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
    return myCellGridDisplay.createGridDisplay();
  }


  private Node makeHistogram(){
    //create the histogram to add it onto the main node
    myHistogram = new HistogramDisplay(myController.getNumCells(), getNumOfEachState());
    return myHistogram.createHistogramDisplay();
  }

  private Node makeInfoDisplay(){
    //make the node with the info display to add it onto the main node
    myInfoDisplay = new InfoDisplay(getNumOfEachState());
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


  private Node makeControls(){
    //make the box with options to pause/resume, do one step, change speed, etc..
    VBox v = new VBox();
    v.getChildren().add(makeShowHideBox());
    v.getChildren().add(makeMiscellaneousControls());
    v.getChildren().add(makeParametersControlBox());
    v.getChildren().add(makeSlider());

    return v;
  }

  private Node makeShowHideBox(){
    //make a node with buttons for users to show/hide the grid, histogram, and info
    HBox showHideBox = new HBox();
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.SHOW_GRID_KEY, () -> myCellGridDisplay.setVisible(true)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.HIDE_GRID_KEY, () -> myCellGridDisplay.setVisible(false)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.SHOW_HISTOGRAM_KEY, () -> myHistogram.setVisible(true)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.HIDE_HISTOGRAM_KEY, () -> myHistogram.setVisible(false)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.SHOW_INFO_KEY, () -> myInfoDisplay.setVisible(true)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.HIDE_INFO_KEY, () -> myInfoDisplay.setVisible(false)));
    return showHideBox;
  }

  private Node makeMiscellaneousControls(){
    //make a node with buttons for users to pause/resume, see about, do one step, or save file
    HBox controlBox = new HBox();
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ABOUT_KEY, () -> showAbout()));
    pauseButton = makeAButton(LanguageResourceHandler.PAUSE_KEY, () -> pauseAnimation());
    resumeButton = makeAButton(LanguageResourceHandler.RESUME_KEY, () -> resumeAnimation());
    resumeButton.setVisible(false);
    controlBox.getChildren().add(pauseButton);
    controlBox.getChildren().add(resumeButton);
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ONE_STEP_KEY, () -> oneStep()));
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.SAVE_FILE_KEY, () -> makePopup()));
    return controlBox;
  }

  private Node makeSlider(){
    //make the slider controlling the speed of the animation
    HBox sliderBox = new HBox();
    Slider slider =new Slider();
    slider.setMin(myViewResourceHandler.getMinFramesPerSecond());
    slider.setMax(myViewResourceHandler.getMaxFramesPerSecond());
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setOnMouseClicked(e -> changeAnimationSpeed(slider.getValue()));
    sliderBox.getChildren().add(makeALabel(LanguageResourceHandler.SPEED_SLIDER_KEY));
    sliderBox.getChildren().add(slider);
    return sliderBox;
  }

  private Node makeParametersControlBox(){
    HBox parametersControlBox = new HBox();

    Node shapeControlBox = makeOptionsBox(LanguageResourceHandler.SHAPES_KEY,
        myViewResourceHandler.getCellShapes(), (s) -> myCellGridDisplay.changeCellShapes(s));
    parametersControlBox.getChildren().add(shapeControlBox);

    Node neighborArrangementBox = makeOptionsBox(LanguageResourceHandler.NEIGHBOR_ARRANGEMENT_KEY,
        myViewResourceHandler.getNeighborArrangements(), (s) -> myController.changeNeighborArrangement(s));
    parametersControlBox.getChildren().add(neighborArrangementBox);

    Node edgePolicyBox = makeOptionsBox(LanguageResourceHandler.EDGE_POLICY_KEY,
        myViewResourceHandler.getEdgePolicies(), (s) -> myController.changeEdgePolicy(s));
    parametersControlBox.getChildren().add(edgePolicyBox);
    return parametersControlBox;
  }



  public void changeAnimationSpeed(double newFramesPerSecond){
    //change speed of animation
    framesPerSecond = newFramesPerSecond;
    secondDelay = 1.0 / framesPerSecond;
    myAnimation.setRate(framesPerSecond);
  }

  private void showAbout(){
    //when the user presses the about button, show them a dialogue box with info about the simulations
    //use the controller to get info about the simulation
    System.out.println("about");
  }



  private void pauseAnimation(){
    //pause teh animation
    myAnimation.pause();
    //pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
    pauseButton.setVisible(false);
    resumeButton.setVisible(true);
    paused = true;
  }

  private void resumeAnimation(){
    //resume the animation
    myAnimation.play();
    //pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    resumeButton.setVisible(false);
    pauseButton.setVisible(true);
    paused = false;
  }

  private void makePopup(){
    //make a poup which the user can interact with to save the simulation
    pauseAnimation();
    FileSavePopup popup = new FileSavePopup(myLanguageResourceHandler, myController);
    popup.makePopup();
  }

  private void oneStep() {
    //go through one step at a time
    pauseAnimation();
    step();
  }


  protected void step() {
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
   * see if the simulation is paused. Only used for testing purposes
   * @return true iff the simulation is paused
   */
  public boolean getPaused(){
    return paused;
  }

  /**
   * returns the second delay of the timeline. Used for testing
   * @return the second delay of the animation
   */
  public double getAnimationSpeed(){
    return secondDelay;
  }

  /**
   * get a list of all the cell displays in the simulation
   * @return allCellDisplays
   */
  public List<CellDisplay> getAllCellDisplays(){
    return myCellGridDisplay.getAllCellDisplays();
  }

  /**
   * get the node containing all the display stuff for this simulation.
   * This will allow us to remove it from the MainView
   * @return myNode
   */
  public Node getMyNode(){
    return myNode;
  }





}
