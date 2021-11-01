package view;

import cellsociety.view.HistogramDisplay;
import java.util.Map;
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
    myHistogramDisplay = new HistogramDisplay(100, stateToCount);
  }

  @Test
  void testSetupOfRectangles(){
    
  }


}
