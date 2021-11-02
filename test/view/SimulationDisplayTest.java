package view;
import cellsociety.cell.CellDisplay;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.CSSidHandler;
import cellsociety.view.MainView;
import cellsociety.view.SimulationDisplay;
import cellsociety.view.ViewUtilities.NodeWithText;
import cellsociety.view.ViewUtilities.Button2;

import java.io.File;
import java.util.Random;

import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;


import java.util.Collection;
public class SimulationDisplayTest extends DukeApplicationTest{
  SimulationDisplay simDisp;
  MainView mView;
  private LanguageResourceHandler resourceHandler;
  private Stage myStage;
  public static final String BLINKERS_PATH = "data/game_of_life/blinkers.sim";

  @Override
  public void start(Stage stage) throws Exception {

    mView = new MainView();
    resourceHandler = mView.getMyResourceHandler();
    stage.setScene(mView.makeSimulationScene());
    mView.handleSelectedFile(new File(BLINKERS_PATH));
    simDisp = mView.getSimDisplay();
    stage.show();
    stage.toFront();
    myStage = stage;

  }

  @Test
  void testPauseAnimation(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    try{
      Button b = findDesiredButton(resourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
      assertEquals(b.getText(), resourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
    }catch (Exception e){
      assertTrue(false);
    }

    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
    try{
      Button b = findDesiredButton(resourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
      assertEquals(b.getText(), resourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    }catch (Exception e){
      assertTrue(false);
    }
  }



  @Test
  void testOneStepButtonSimple(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.ONE_STEP_KEY));
    try {
      Button b = findDesiredButton(resourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
      assertTrue(true);
    } catch (Exception e){
      assertTrue(false);
    }
  }

  @Test
  void testOneStepButtonComplex(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.ONE_STEP_KEY));
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.ONE_STEP_KEY));
    try {
      Button b = findDesiredButton(resourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
      assertTrue(true);
    } catch (Exception e){
      assertTrue(false);
    }

    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.ONE_STEP_KEY));
    try {
      Button b = findDesiredButton(resourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
      assertTrue(true);
    } catch (Exception e){
      assertTrue(false);
    }
  }

  @Test
  void testChangeSpeedSimple(){
    Node rootNode = myStage.getScene().getRoot();
    Slider s = from(rootNode).lookup(".slider").query();
    changeSpeedSlider(s, 30);
    assertTrue(doublesEqual(1.0/30.0,  simDisp.getAnimationSpeed()));
  }

  @Test
  void testChangeSpeedComplex(){
    Node rootNode = myStage.getScene().getRoot();
    Slider s = from(rootNode).lookup(".slider").query();
    changeSpeedSlider(s, 30);
    assertTrue(doublesEqual(1.0/30, simDisp.getAnimationSpeed()));

    changeSpeedSlider(s, 1);
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    testOneStepButtonComplex();
    changeSpeedSlider(s, 25);
    assertTrue(doublesEqual(1.0/25.0, simDisp.getAnimationSpeed()));
  }

  private void changeSpeedSlider(Slider s, double newFPS){
    setValue(s, newFPS);
    //line above actually does nothing since it isn't technicaly cliking on the slider
  }

  @Test
  void testClickCellSimple(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    CellDisplay cellDisp = simDisp.getAllCellDisplays().get(0);
    int state0 = cellDisp.getState();
    Node dispNode = cellDisp.getMyDisplay();
    clickOn(dispNode);
    int state1 = cellDisp.getState();
    assertEquals(state1, (state0 + 1) % cellDisp.getStateColors().length);
  }

  @Test
  void testClickCellComplex(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.PAUSE_KEY));
    CellDisplay cellDisp = simDisp.getAllCellDisplays().get(7);
    int state0 = cellDisp.getState();
    Node dispNode = cellDisp.getMyDisplay();
    for (int i = 0; i < 10; i++) {
      clickOn(dispNode);
    }
    int state1 = cellDisp.getState();
    assertEquals(state1, (state0 + 10) % cellDisp.getStateColors().length);
  }

  private boolean doublesEqual(double d1, double d2){
    return (Math.abs(d1 - d2)) < 0.001;
  }

  private Button findDesiredButton(String correctText) throws Exception{
    Node rootNode = myStage.getScene().getRoot();
    Collection<Button> buttons = from(rootNode).lookup(".button").queryAll();
    for (Button b : buttons){
      try{
        NodeWithText n = (Button2) b;
        String actualText = n.getText();
        if (actualText.equals(correctText)){
          return b;
        }
      } catch (Exception e){
        throw new Exception();
      }
    }
    throw new Exception();
  }



}
