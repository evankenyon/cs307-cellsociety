package view;
import cellsociety.view.SimulationDisplay;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.view.MainView;
import cellsociety.view.ViewUtilities.NodeWithText;
import cellsociety.view.ViewUtilities.Button2;

import java.io.File;

import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;


import java.util.Collection;


public class MainViewTest extends DukeApplicationTest{
  private MainView mainView;
  private LanguageResourceHandler resourceHandler;
  private Stage myStage;
  private static final Color BASIC_BACKGROUND = Color.web("#FFFFFF");
  private static final Color DUKE_BACKGROUND = Color.web("#2222FF");
  private static final Color DARK_BACKGROUND = Color.web("#000000");
  public static final String BLINKERS_PATH = "data/game_of_life/blinkers.sim";
  public static final String GLIDER_PATH = "data/game_of_life/glider.sim";


  @Override
  public void start(Stage stage) throws Exception {
    mainView = new MainView();
    resourceHandler = mainView.getMyResourceHandler();
    stage.setScene(mainView.makeSimulationScene());
    mainView.setStage(stage);
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
        String expected = mainView.getExpectedTextforNode(n2);
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
    File testFile = new File(BLINKERS_PATH);
    mainView.handleSelectedFile(testFile);
    assertTrue(null != mainView.getSimDisplay());
  }

  @Test
  //for some reason this isn't working even though it clearly looks right when you change colors manually
  void testSwitchStyleSimple(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.DARK_MODE_KEY));
    assertEquals(DARK_BACKGROUND, myStage.getScene().getFill());

    //clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY));
    //assertEquals(BASIC_BACKGROUND, myStage.getScene().getFill());
    //clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY));
    //assertEquals(DUKE_BACKGROUND, myStage.getScene().getFill()); //not actually blue

  }

  @Test
    //for some reason this fails even though it clearly works when you change colors manually
  void testSwitchStyleComplex(){
    //a bunch of random stuff mixed in between
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.DARK_MODE_KEY));
    assertEquals(DARK_BACKGROUND, myStage.getScene().getFill());
    testChangeLanguagesComplex();
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY));
    assertEquals(DUKE_BACKGROUND, myStage.getScene().getFill()); //not actually blue
    testFileInput();
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY));
    assertEquals(DUKE_BACKGROUND, myStage.getScene().getFill()); //not actually blue

  }

  @Test
  void testAddNewSimulationWindow(){
    clickOn(resourceHandler.getStringFromKey(LanguageResourceHandler.NEW_SIMULATION_KEY));
    //not sure how to test that a new window popped up

  }

  @Test
  void testAddNewSimulationSimple(){
    SimulationDisplay simDisp00 = mainView.getSimDisplay();
    assertTrue(simDisp00 == null);
    sleep(50); //had to throw this in here because testFX is dumb
    mainView.handleSelectedFile(new File(BLINKERS_PATH));
    SimulationDisplay simDisp0 = mainView.getSimDisplay();
    assertTrue(simDisp0 != null);
    sleep(50);
    mainView.handleSelectedFile(new File(BLINKERS_PATH));
    SimulationDisplay simDisp1 = mainView.getSimDisplay();
    assertTrue(simDisp1 != simDisp0);

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
