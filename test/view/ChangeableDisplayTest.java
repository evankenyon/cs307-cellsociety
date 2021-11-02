package view;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.Group;

import cellsociety.view.ChangeableDisplay;
import cellsociety.view.MainView;
import cellsociety.resourceHandlers.LanguageResourceHandler;

public class ChangeableDisplayTest extends DukeApplicationTest{
  private ChangeableDisplay cd;
  private LanguageResourceHandler myHandler;
  private int testVar;
  private Group myGroup;



  @Override
  public void start(Stage stage) throws Exception {
    myGroup = new Group();
    stage.setScene(new Scene(myGroup, 400, 400));
  }



  @BeforeEach()
  void setup(){
    cd = new MainView();
    myHandler = new LanguageResourceHandler();
    testVar = 0;
  }

  @Test
  void testMakeLabel(){
    //fails for some reason, not sure why. Line below crashes - Keith
    Label l = cd.makeALabel(LanguageResourceHandler.SELECT_FILE_KEY);

    assertEquals(myHandler.getStringFromKey(LanguageResourceHandler.SELECT_FILE_KEY), l.getText());
  }

  @Test
  void testMakeButton(){
    //fails for some reason, not sure why. Line below crashes - Keith
    Button b = cd.makeAButton(LanguageResourceHandler.EDGE_POLICY_KEY, () -> setTestVar(12));
    myGroup.getChildren().add(b);
    clickOn(b);
    assertEquals(12, testVar);
    assertEquals(myHandler.getStringFromKey(LanguageResourceHandler.EDGE_POLICY_KEY), b.getText());
  }

  private void setTestVar(int x){
    testVar = x;
  }

}
