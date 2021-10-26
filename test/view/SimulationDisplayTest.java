package view;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.view.MainView;
import cellsociety.view.SimulationDisplay;
import cellsociety.view.NodeWithText;
import cellsociety.view.Button2;

import java.io.File;
import java.util.Random;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.util.Collection;
public class SimulationDisplayTest extends DukeApplicationTest{
  SimulationDisplay simDisp;
  MainView mView;
  private LanguageResourceHandler resourceHandler;
  private Stage myStage;

  @Override
  public void start(Stage stage) throws Exception {

    mView = new MainView();
    resourceHandler = mView.getMyResourceHandler();
    stage.setScene(mView.makeSimulationScene());
    mView.handleSelectedFile(new File("data/game_of_life/blinkers.csv"));
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
  void testSaveFile(){

    Node rootNode = myStage.getScene().getRoot();
    TextField fileNameField = from(rootNode).lookup(".text-field").query();
    Random r = new Random();
    int fileName = r.nextInt(9999999);

    writeInputTo((TextInputControl)fileNameField, String.valueOf(fileName));
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.SAVE_FILE_KEY));
    try {
      File f = new File("data/game_of_life/saved/program-" + fileName + ".csv");
      assertTrue(f.exists());
    } catch (Exception e){
      assertTrue(false);
    }

  }

  @Test
  void testOneStepButton(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.ONE_STEP_KEY));
    try {
      Button b = findDesiredButton(resourceHandler.getStringFromKey(LanguageResourceHandler.RESUME_KEY));
      assertTrue(true);
    } catch (Exception e){
      assertTrue(false);
    }
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
