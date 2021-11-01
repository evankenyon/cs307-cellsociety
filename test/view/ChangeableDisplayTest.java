package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import cellsociety.view.ChangeableDisplay;
import cellsociety.view.MainView;
import cellsociety.resourceHandlers.LanguageResourceHandler;

public class ChangeableDisplayTest {
  private ChangeableDisplay cd;
  private LanguageResourceHandler myHandler;
  private int testVar;


  @BeforeEach()
  void setup(){
    cd = new MainView();
    myHandler = new LanguageResourceHandler();
    testVar = 0;
  }

  @Test
  void testMakeLabel(){
    //fails for some reason, not sure why. Line below crashes - Keith
    Label l = cd.makeALabel(LanguageResourceHandler.EDGE_POLICY_KEY);

    assertEquals(myHandler.getStringFromKey(LanguageResourceHandler.LANGUAGE_KEY), l.getText());
  }

  @Test
  void testMakeButton(){
    //fails for some reason, not sure why. Line below crashes - Keith
    Button b = cd.makeAButton(LanguageResourceHandler.EDGE_POLICY_KEY, () -> setTestVar(12));
    assertEquals(12, testVar);
    assertEquals(myHandler.getStringFromKey(LanguageResourceHandler.EDGE_POLICY_KEY), b.getText());
  }

  private void setTestVar(int x){
    testVar = x;
  }
}
