package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.ViewResourceHandler;
import cellsociety.controller.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;

import cellsociety.cell.Cell;
import cellsociety.cell.CellDisplay;


import java.util.List;
import java.util.ArrayList;

/**
 * Objects of this class represent the display for a single simulation (I say single since there can be multiple
 * simulations on the screen at once).
 * @author Keith Cressman
 */
public class SimulationDisplay extends ChangeableDisplay{

  protected double framesPerSecond = 1;
  protected double secondDelay = 1.0 / framesPerSecond;
  private Controller myController;
  protected Timeline myAnimation;
  private boolean paused;
  private Button pauseButton, resumeButton;
  private TextField fileNameField;
  private ViewResourceHandler myViewResourceHandler;
  private List<CellDisplay> allCellDisplays;
  private Node myNode;

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
    }
    VBox root = new VBox();
    root.getChildren().add(makeCellsAndBackground());
    root.getChildren().add(makeControls());
    setUpAnimation();
    myNode = root;
    return root;
  }

  private Node makeCellsAndBackground(){
    //make a Node containing a background and all the cell displays on top of it
    Group simAreaGroup = new Group();
    simAreaGroup.getChildren().add(new Rectangle(0, 0, myViewResourceHandler.simulationWidth(), myViewResourceHandler.simulationWidth()));
    //simAreaGroup.getChildren().addAll(myController.getCellDisplays());
    simAreaGroup.getChildren().addAll(makeCellDisplays());
    return simAreaGroup;
  }

  private List<Node> makeCellDisplays(){
    //take all the cells in the simulation and create a cell display for them,
    allCellDisplays = new ArrayList<>();
    List<Node> displayNodes = new ArrayList<>();
    List<Cell> allCells = myController.getCells();
    int[] gridShape = myController.getGridShape();
    int numRows = gridShape[0];
    int numCols = gridShape[1];
    double widthPerCell = myViewResourceHandler.simulationWidth() / (numCols + 0.01);
    double heightPerCell = myViewResourceHandler.simulationWidth() / (numRows + 0.01);
    for (Cell cell : allCells){
      displayNodes.add(makeACellDisplay(cell, widthPerCell, heightPerCell).getMyDisplay());
    }
    return displayNodes;
  }

  private CellDisplay makeACellDisplay(Cell cell, double widthPerCell, double heightPerCell){
    //make a cell display for the cell with the width and height given as arguments
    CellDisplay newDisplay = new CellDisplay(cell.getjIndex() * widthPerCell,
        cell.getiIndex() * heightPerCell, widthPerCell, heightPerCell, cell.getCurrentState());
    newDisplay.setCell(cell);
    newDisplay.setColors(myViewResourceHandler.getColorsForSimulation(
        myController.getSimulationType()));
    cell.setDisplay(newDisplay);
    allCellDisplays.add(newDisplay);
    return newDisplay;
  }

  private Node makeControls(){
    VBox v = new VBox();
    HBox controlBox = new HBox();
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ABOUT_KEY, () -> showAbout()));
    pauseButton = makeAButton(LanguageResourceHandler.PAUSE_KEY, () -> pauseAnimation());
    resumeButton = makeAButton(LanguageResourceHandler.RESUME_KEY, () -> resumeAnimation());
    resumeButton.setVisible(false);
    controlBox.getChildren().add(pauseButton);
    controlBox.getChildren().add(resumeButton);
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ONE_STEP_KEY, () -> oneStep()));
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.SAVE_FILE_KEY, () -> makePopup()));
    v.getChildren().add(controlBox);
    fileNameField = new TextField();
    v.getChildren().add(fileNameField);
    v.getChildren().add(makeSlider());
    return v;
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



  private void playPauseSimulation(){
    //pause or resume the simulation
    if (paused){
      resumeAnimation();
    }else{
      pauseAnimation();
    }
  }

  private void pauseAnimation(){
    myAnimation.pause();
    //pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
    pauseButton.setVisible(false);
    resumeButton.setVisible(true);
    paused = true;
  }

  private void resumeAnimation(){
    myAnimation.play();
    //pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    resumeButton.setVisible(false);
    pauseButton.setVisible(true);
    paused = false;
  }

//  private void saveFile(){
//    //save the simulation, with a file name specified by user
//    try {
//      String fileName = fileNameField.getText();
//      myController.saveFile(fileName);
//    } catch (Exception e){
//      displayErrorMessage(e.getMessage());
//    }
//  }

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
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      // TODO: move to props file
      displayErrorMessage("Reflection error occurred in backend model, please try restarting the"
          + "program");
    }

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
    return Collections.unmodifiableList(allCellDisplays);
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
