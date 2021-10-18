package view;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.view.SimulationView;
import cellsociety.view.NodeWithText;
import cellsociety.view.Button2;



import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;


import java.util.Map;
import java.util.HashMap;
import java.util.Collection;


public class SimulationViewTest extends DukeApplicationTest{
  private SimulationView simView;
  private LanguageResourceHandler resourceHandler;
  private Stage myStage;



  @Override
  public void start(Stage stage) throws Exception {
    simView = new SimulationView();
    resourceHandler = new LanguageResourceHandler();
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
  void testPressSpanish(){
    testChangeLanguage(resourceHandler.getChangeSpanishString());
  }

  @Test
  void testPressEnglish(){
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
