package view;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.view.MainView;
import cellsociety.view.NodeWithText;
import cellsociety.view.Button2;



import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;


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
      Button languageButton = findDesiredButton(languageButtonText);
      clickOn(languageButton);
      testTextComponents();
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
