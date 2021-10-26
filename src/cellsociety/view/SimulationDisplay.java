package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.ViewResourceHandler;
import cellsociety.controller.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;


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
  private Button pauseButton, oneStepButton;
  private TextField fileNameField;
  private ViewResourceHandler myViewResourceHandler;

  public SimulationDisplay(){
    this(new LanguageResourceHandler());
  }

  public SimulationDisplay(LanguageResourceHandler l){
    super(l);
    myController = new Controller();
    myViewResourceHandler = new ViewResourceHandler();
  }

  protected void setUpAnimation(){
    initializeAnimation();

    myAnimation.play();
    paused = false;
  }

  private void initializeAnimation(){
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(secondDelay), e -> step()));
  }

  /**
   * create the display (i.e. a grid with each cell) to put on the MainView
   * @return a Node to display on the MainView
   */
  public Node makeDisplay(File CSVFile) throws Exception{
    try {
      myController.parseFile(CSVFile);
    }catch (Exception e){
      e.printStackTrace();
      throw new Exception(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.BAD_FILE_KEY));
    }
    VBox root = new VBox();
    Group simAreaGroup = new Group();
    simAreaGroup.getChildren().add(new Rectangle(0, 0, myViewResourceHandler.simulationWidth(), myViewResourceHandler.simulationWidth()));
    simAreaGroup.getChildren().addAll(myController.getCellDisplays());
    root.getChildren().add(simAreaGroup);
    root.getChildren().add(makeControls());
    setUpAnimation();
    return root;
  }

  private Node makeControls(){
    VBox v = new VBox();
    HBox controlBox = new HBox();
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ABOUT_KEY, () -> showAbout()));
    pauseButton = makeAButton(LanguageResourceHandler.PAUSE_KEY, () -> playPauseSimulation());
    oneStepButton = makeAButton(LanguageResourceHandler.ONE_STEP_KEY, () -> oneStep());
    controlBox.getChildren().add(pauseButton);
    controlBox.getChildren().add(oneStepButton);
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.SAVE_FILE_KEY, () -> saveFile()));
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
    initializeAnimation();
    if (!paused){
      myAnimation.play();
    }


  }

  private void showAbout(){
    //when the user presses the about button, show them a dialogue box with info about the simulations
    //use the controller to get info about the simulation
    System.out.println("about");
  }



  private void playPauseSimulation(){
    //pause or resume the simulation
    if (paused){
      myAnimation.play();
      pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    }else{
      myAnimation.pause();
      pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
    }
    paused = !paused;
  }

  private void saveFile(){
    //save the simulation, with a file name specified by user
    try {
      String fileName = fileNameField.getText();
      myController.saveFile(fileName);
    } catch (Exception e){
      displayErrorMessage(e.getMessage());
    }
  }

  private void oneStep(){
    //go through one step at a time
    myAnimation.pause();
    pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
    paused = true;

    step();
  }

  protected void step(){
    myController.step();
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



}
