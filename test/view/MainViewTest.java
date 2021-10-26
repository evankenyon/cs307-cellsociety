package view;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.view.MainView;
import cellsociety.view.NodeWithText;
import cellsociety.view.Button2;

import java.io.File;

import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.util.Collection;


public class MainViewTest extends DukeApplicationTest{
  private MainView simView;
  private LanguageResourceHandler resourceHandler;
  private Stage myStage;



  @Override
  public void start(Stage stage) throws Exception {
    simView = new MainView();
    resourceHandler = simView.getMyResourceHandler();
    stage.setScene(simView.makeSimulationScene());
    simView.setStage(stage);
    stage.show();
    stage.toFront();
    myStage = stage;
  }

  @Test
  public void testTextComponents() {
    Node rootNode = myStage.getScene().getRoot();
    Collection<Node> nodes = from(rootNode).lookup(".button").queryAll();
    nodes.addAll(from(rootNode).lookup(".label").queryAll());

    for (Node n : nodes){
      try{
        NodeWithText n2 = ((NodeWithText)n);
        String expected = simView.getExpectedTextforNode(n2);
        String actual = n2.getText();
        assertEquals(expected, actual);
      } catch (Exception e){

      }
    }

  }

  @Test
  void testPressSpanishSimple(){
    testChangeLanguage(resourceHandler.getChangeSpanishString());
  }

  @Test
  void testPressEnglish(){
    testChangeLanguage(resourceHandler.getChangeEnglishString());
  }

  @Test
  void testChangeLanguagesComplex(){
    testChangeLanguage(resourceHandler.getChangeSpanishString());
    testChangeLanguage(resourceHandler.getChangeSpanishString());
    testChangeLanguage(resourceHandler.getChangeEnglishString());
    testChangeLanguage(resourceHandler.getChangeSpanishString());
    testChangeLanguage(resourceHandler.getChangeEnglishString());
  }

  void testChangeLanguage(String languageButtonText){
    //Need to change this later to involve actually clicking the button
    Node rootNode = myStage.getScene().getRoot();
    try {
      clickOn(languageButtonText);
      testTextComponents();
    } catch (Exception e){
      assertTrue(false);
    }
  }

  @Test
  //not sure how to test clicking buttons on the file chooser
  void testFileInput(){
    testPressEnglish();
    File testFile = new File("data/game_of_life/blinkers.csv");
    simView.handleSelectedFile(testFile);
    assertEquals(1, simView.getNumSimulations());
  }

  @Test
  //need to figure out how to check colorw
  void testSwitchStyleSimple(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.DARK_MODE_KEY));
    assertEquals(Color.BLACK, myStage.getScene().getFill());
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY));
    assertEquals(Color.WHITE, myStage.getScene().getFill());
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY));
    assertEquals(Color.BLUE, myStage.getScene().getFill()); //not actually blue
  }

  @Test
    //need to figure out how to check colors
  void testSwitchStyleComplex(){
    //a bunch of random stuff mixed in between
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.DARK_MODE_KEY));
    assertEquals(Color.BLACK, myStage.getScene().getFill());
    testChangeLanguagesComplex();
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY));
    assertEquals(Color.BLUE, myStage.getScene().getFill()); //not actually blue
    testFileInput();
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY));
    assertEquals(Color.BLUE, myStage.getScene().getFill()); //not actually blue
  }

  @Test
  void testAddNewSimulation(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.NEW_SIMULATION_KEY));
    //not sure how to test that a new window popped up
    
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
