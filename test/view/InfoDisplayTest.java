package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Label;

import cellsociety.view.InfoDisplay;
import java.util.Map;
import java.util.HashMap;

public class InfoDisplayTest {
  private InfoDisplay myInfoDisplay;

  @BeforeEach
  void setup(){
    Map<Integer, Integer> stateToCount = new HashMap<>();
    stateToCount.put(0, 20);
    stateToCount.put(1, 60);
    stateToCount.put(2, 20);
    myInfoDisplay = new InfoDisplay(stateToCount);
  }

  @Test
  void testSetup(){
    Map<Integer, Label> stateToNumChangeLabel = myInfoDisplay.getStateToNumChangeLabel();
    for (Integer state : stateToNumChangeLabel.keySet()){
      assertEquals("0", stateToNumChangeLabel.get(state).getText());
    }
  }

  @Test
  void testSetNumOfEachType(){
    Map<Integer, Integer> stateToCount = new HashMap<>();
    stateToCount.put(0, 20);
    stateToCount.put(1, 20);
    stateToCount.put(2, 60);
    myInfoDisplay.setNumOfEachType(stateToCount);
    Map<Integer, Label> stateToNumChangeLabel = myInfoDisplay.getStateToNumChangeLabel();
    assertEquals("0",  stateToNumChangeLabel.get(0).getText());
    assertEquals("-40",  stateToNumChangeLabel.get(1).getText());
    assertEquals("40",  stateToNumChangeLabel.get(2).getText());
  }

}
