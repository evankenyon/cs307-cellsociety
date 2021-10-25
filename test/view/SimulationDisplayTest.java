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

    writeInputTo((TextInputControl)fileNameField, "testName");
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.SAVE_FILE_KEY));
    //assert somethig

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
