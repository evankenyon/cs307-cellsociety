package cellsociety.view;

import cellsociety.CornerLocationGenerator.HexagonalCellCornerLocationGenerator;
import cellsociety.CornerLocationGenerator.TriangularCellCornerLocationGenerator;
import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.ViewResourceHandler;
import cellsociety.controller.Controller;

import java.io.File;
import java.io.FileNotFoundException;
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
import cellsociety.CornerLocationGenerator.RectangleCellCornerLocationGenerator;
import cellsociety.CornerLocationGenerator.CornerLocationGenerator;
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
  private HistogramDisplay myHistogram;

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
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(secondDelay), e -> {
      try {
        step();
      } catch (InvocationTargetException ex) {
        ex.printStackTrace();
      } catch (NoSuchMethodException ex) {
        ex.printStackTrace();
      } catch (IllegalAccessException ex) {
        ex.printStackTrace();
      }
    }));

    myAnimation.play();
    paused = false;
  }


  /**
   * create the display (i.e. a grid with each cell) to put on the MainView
   * @return a Node to display on the MainView
   */
  public Node makeDisplay(File SimFile) throws Exception{
    try {
      myController.parseFile(SimFile);
    }catch (Exception e){
      e.printStackTrace();
      throw new Exception(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.BAD_FILE_KEY));
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
    simulationsBox.getChildren().add(makeCellsAndBackground());
    simulationsBox.getChildren().add(makeHistogram());
    return simulationsBox;
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
   // CellDisplay newDisplay = new CellDisplay(cell.getjIndex() * widthPerCell,
        //cell.getiIndex() * heightPerCell, widthPerCell, heightPerCell, cell.getCurrentState());
    CellDisplay newDisplay = new CellDisplay(generateXYs(cell), cell.getCurrentState());
    newDisplay.setCell(cell);
    newDisplay.setColors(myViewResourceHandler.getColorsForSimulation(
        myController.getSimulationType()));
    cell.setDisplay(newDisplay);
    allCellDisplays.add(newDisplay);
    return newDisplay;
  }

  private double[] generateXYs(Cell cell){
    int i = cell.getiIndex();
    int j = cell.getjIndex();
    CornerLocationGenerator cornerGenerator = new HexagonalCellCornerLocationGenerator(myController.getGridShape()[0], myController.getGridShape()[1]);
    List<CornerLocation> locations = cornerGenerator.generateCorners(i, j);
    double[] retXYs = new double[2*locations.size()];
    int index = 0;
    for (CornerLocation corner : locations){
      retXYs[2*index] = corner.getX_pos();
      retXYs[2*index + 1] = corner.getY_pos();
      index = index + 1;
    }
    return retXYs;

  }

  private Node makeHistogram(){
    //create the histogram to add it onto the main node
    myHistogram = new HistogramDisplay(allCellDisplays.size(), getNumOfEachState());
    return myHistogram.createHistogramDisplay();
  }

  /**
   * Create/return a map that maps each state to the number of cells in that state
   * This method should probably go in model, I don't like having it in here.
   * @return a map used to find how many cells in each state
   */
  private Map<Integer, Integer> getNumOfEachState(){
    Map<Integer, Integer> stateToCount = new HashMap<>();
    for (CellDisplay c : allCellDisplays){
      int state = c.getState();
      stateToCount.putIfAbsent(state, 0);
      stateToCount.put(state, 1 + stateToCount.get(state));
    }
    return stateToCount;
  }

  private Node makeControls(){
    //make the box with options to pause/resume, do one step, change speed, etc..
    VBox v = new VBox();
    HBox controlBox = new HBox();
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ABOUT_KEY, () -> showAbout()));
    pauseButton = makeAButton(LanguageResourceHandler.PAUSE_KEY, () -> pauseAnimation());
    resumeButton = makeAButton(LanguageResourceHandler.RESUME_KEY, () -> resumeAnimation());
    resumeButton.setVisible(false);
    controlBox.getChildren().add(pauseButton);
    controlBox.getChildren().add(resumeButton);
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ONE_STEP_KEY, () -> {
      try {
        oneStep();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }));
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.SAVE_FILE_KEY, () -> makePopup()));
    v.getChildren().add(controlBox);
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


  private void makePopup(){
    //make a poup which the user can interact with to save the simulation
    pauseAnimation();
    FileSavePopup popup = new FileSavePopup(myLanguageResourceHandler, myController);
    popup.makePopup();
  }

  private void oneStep() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    //go through one step at a time
    pauseAnimation();
    step();
  }

  protected void step() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    myController.step();
    myHistogram.setNumOfEachType(getNumOfEachState());
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
