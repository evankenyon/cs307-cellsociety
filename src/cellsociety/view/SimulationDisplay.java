package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;
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

import java.util.List;
import java.util.ArrayList;

/**
 * Objects of this class represent the display for a single simulation (I say single since there can be multiple
 * simulations on the screen at once).
 * @author Keith Cressman
 */
public class SimulationDisplay extends ChangeableDisplay{
  public static final int DEFAULT_WIDTH = 400; //should go into resources
  protected double secondDelay = 1.0 / 60.0;
  private Controller myController;
  protected Timeline myAnimation;

  public SimulationDisplay(){
    this(new LanguageResourceHandler());
  }

  public SimulationDisplay(LanguageResourceHandler l){
    super(l);
    myController = new Controller();
    setUpAnimation();
  }

  protected void setUpAnimation(){
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(secondDelay), e -> step()));
    myAnimation.play();
  }

  /**
   * create the display (i.e. a grid with each cell) to put on the MainView
   * @return a Node to display on the MainView
   */
  public Node makeDisplay(File CSVFile) throws Exception{
    try {
      myController.parseFile(CSVFile);
    }catch (Exception e){
      throw new Exception(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.BAD_FILE_KEY));
    }
    VBox root = new VBox();
    Group simAreaGroup = new Group();
    simAreaGroup.getChildren().add(new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_WIDTH));
    simAreaGroup.getChildren().addAll(myController.getCellDisplays()));
    root.getChildren().add(simAreaGroup);
    root.getChildren().add(makeControls());
    return root;
  }

  private Node makeControls(){
    HBox controlBox = new HBox();
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ABOUT_KEY, () -> showAbout()));
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.START_SIMULATIONS_KEY, () -> startSimulation()));
    return controlBox;
  }

  private void showAbout(){
    //when the user presses the about button, show them a dialogue box with info about the simulations
    //use the controller to get info about the simulation
    System.out.println("about");
  }


  private void pauseAnimation(){

  }

  private void resumeAnimation(){

  }

  private void startSimulation(){

  }

  protected void step(){
    //use controller to update the model
    //myController.step();
  }





}
