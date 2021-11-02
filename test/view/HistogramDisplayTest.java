package view;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import javafx.scene.shape.Rectangle;

import cellsociety.view.HistogramDisplay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Map;
import java.util.HashMap;

public class HistogramDisplayTest {
  private HistogramDisplay myHistogramDisplay;

  @BeforeEach
  void setup(){

    Map<Integer, Integer> stateToCount = new HashMap<>();
    stateToCount.put(0, 20);
    stateToCount.put(1, 60);
    stateToCount.put(2, 20);
    myHistogramDisplay = new HistogramDisplay(100, stateToCount, new LanguageResourceHandler());
  }

  @Test
  void testSetupOfRectangles(){
    Map<Integer, Rectangle> stateToBars = myHistogramDisplay.getHistogramBars();
    Rectangle rect0 = stateToBars.get(0);
    Rectangle rect1 = stateToBars.get(1);
    Rectangle rect2 = stateToBars.get(2);
    assertTrue(rect0.getHeight() == rect2.getHeight());
    assertTrue(rect1.getHeight() == 3 * rect0.getHeight());
  }

  @Test
  void testSetNumOfEachType(){
    Map<Integer, Integer> stateToCount = new HashMap<>();
    stateToCount.put(0, 20);
    stateToCount.put(1, 40);
    stateToCount.put(2, 40);
    Map<Integer, Rectangle> stateToBars = myHistogramDisplay.getHistogramBars();
    Rectangle rect0 = stateToBars.get(0);
    Rectangle rect1 = stateToBars.get(1);
    Rectangle rect2 = stateToBars.get(2);
    myHistogramDisplay.setNumOfEachType(stateToCount);
    assertTrue(rect1.getHeight() == rect2.getHeight());
    assertTrue(rect1.getHeight() == 2 * rect0.getHeight());
  }


}
