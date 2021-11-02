package cellsociety.view;

import cellsociety.cell.IllegalCellStateException;
import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.controller.Controller;

import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Objects of this class represent the display of the settings for the simulation
 *
 * @author Keith Cressman
 */
public class SettingsDisplay extends ChangeableDisplay {

  private double framesPerSecond = 1;
  private double secondDelay = 1.0 / framesPerSecond;

  private Controller myController;
  private Button pauseButton, resumeButton;
  private boolean paused;
  private Timeline myAnimation;
  private SimulationDisplay mySimulationDisplay;


  /**
   * create a settingsDisplay with the specified controller and language resource handler
   *
   * @param controller will be myController
   * @param l          will be myLanguageResourceHandler
   */
  public SettingsDisplay(Controller controller, LanguageResourceHandler l,
      SimulationDisplay simDisp) {
    super(l);
    myController = controller;
    mySimulationDisplay = simDisp;
  }

  /**
   * get the node representing the display for hte
   *
   * @return
   */
  public Node createSettingsDisplay() {
    VBox v = new VBox();
    v.getChildren().add(makeShowHideBox());
    v.getChildren().add(makeMiscellaneousControls());
    v.getChildren().add(makeParametersControlBox());
    v.getChildren().add(makeSlider());
    setUpAnimation();
    myDisp = v;

    return v;
  }

  private Node makeShowHideBox() {
    //make a node with buttons for users to show/hide the grid, histogram, and info
    HBox showHideBox = new HBox();

    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.SHOW_GRID_KEY,
        () -> mySimulationDisplay.showGrid(true)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.HIDE_GRID_KEY,
        () -> mySimulationDisplay.showGrid(false)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.SHOW_HISTOGRAM_KEY,
        () -> mySimulationDisplay.showHistogram(true)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.HIDE_HISTOGRAM_KEY,
        () -> mySimulationDisplay.showHistogram(false)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.SHOW_INFO_KEY,
        () -> mySimulationDisplay.showInfoDisplay(true)));
    showHideBox.getChildren().add(makeAButton(LanguageResourceHandler.HIDE_INFO_KEY,
        () -> mySimulationDisplay.showInfoDisplay(false)));

    return showHideBox;

  }

  private Node makeMiscellaneousControls() {
    //make a node with buttons for users to pause/resume, see about, do one step, or save file
    HBox controlBox = new HBox();
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ABOUT_KEY, () -> showAbout()));
    pauseButton = makeAButton(LanguageResourceHandler.PAUSE_KEY, () -> pauseAnimation());
    resumeButton = makeAButton(LanguageResourceHandler.RESUME_KEY, () -> resumeAnimation());
    resumeButton.setVisible(false);
    controlBox.getChildren().add(pauseButton);
    controlBox.getChildren().add(resumeButton);
    controlBox.getChildren()
        .add(makeAButton(LanguageResourceHandler.ONE_STEP_KEY, () -> oneStep()));
    controlBox.getChildren()
        .add(makeAButton(LanguageResourceHandler.SAVE_FILE_KEY, () -> makeFileSavePopup()));
    return controlBox;
  }

  private Node makeSlider() {
    //make the slider controlling the speed of the animation
    HBox sliderBox = new HBox();
    Slider slider = new Slider();
    slider.setMin(getMyViewResourceHandler().getMinFramesPerSecond());
    slider.setMax(getMyViewResourceHandler().getMaxFramesPerSecond());
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setOnMouseClicked(e -> changeAnimationSpeed(slider.getValue()));
    sliderBox.getChildren().add(makeALabel(LanguageResourceHandler.SPEED_SLIDER_KEY));
    sliderBox.getChildren().add(slider);
    return sliderBox;
  }

  private Node makeParametersControlBox() {

    HBox parametersControlBox = new HBox();

    Node shapeControlBox = makeOptionsBox(LanguageResourceHandler.SHAPES_KEY,
        getMyViewResourceHandler().getCellShapes(), (s) -> mySimulationDisplay.changeCellShapes(s));
    parametersControlBox.getChildren().add(shapeControlBox);

    Node neighborArrangementBox = makeOptionsBox(LanguageResourceHandler.NEIGHBOR_ARRANGEMENT_KEY,
        getMyViewResourceHandler().getNeighborArrangements(), (s) -> {
          try {
            myController.changeNeighborArrangement(s);
          } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            displayErrorMessage(myLanguageResourceHandler.getStringFromKey(
                LanguageResourceHandler.FAILED_REFLECT_KEY));
          }
        });
    parametersControlBox.getChildren().add(neighborArrangementBox);

    Node edgePolicyBox = makeOptionsBox(LanguageResourceHandler.EDGE_POLICY_KEY,
        getMyViewResourceHandler().getEdgePolicies(), (s) -> myController.changeEdgePolicy(s));
    parametersControlBox.getChildren().add(edgePolicyBox);
    return parametersControlBox;
  }


  public void changeAnimationSpeed(double newFramesPerSecond) {
    //change speed of animation
    framesPerSecond = newFramesPerSecond;
    secondDelay = 1.0 / framesPerSecond;
    myAnimation.setRate(framesPerSecond);
  }

  private void showAbout() {
    //when the user presses the about button, show them a dialogue box with info about the simulations
    //use the controller to get info about the simulation
    System.out.println("about");
  }


  private void pauseAnimation() {
    //pause teh animation
    myAnimation.pause();
    //pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
    pauseButton.setVisible(false);
    resumeButton.setVisible(true);
    paused = true;
  }

  private void resumeAnimation() {
    //resume the animation
    myAnimation.play();
    //pauseButton.setText(myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    resumeButton.setVisible(false);
    pauseButton.setVisible(true);
    paused = false;
  }

  private void makeFileSavePopup() {
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


  private void step() {
    //called at each time step of the animation
    mySimulationDisplay.step();
  }


  protected void setUpAnimation() {
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(secondDelay), e -> step()));
    myAnimation.play();
    paused = false;
  }

  /**
   * get the speed of the animation;
   *
   * @return secondDelay
   */
  public double getAnimationSpeed() {
    return secondDelay;
  }

}
