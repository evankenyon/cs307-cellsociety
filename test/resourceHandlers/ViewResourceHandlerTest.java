package resourceHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import static org.junit.jupiter.api.Assertions.*;
import cellsociety.resourceHandlers.ViewResourceHandler;
import cellsociety.CornerLocationGenerator.CornerLocationGenerator;
import cellsociety.CornerLocationGenerator.RectangleCellCornerLocationGenerator;
import cellsociety.CornerLocationGenerator.TriangularCellCornerLocationGenerator;
import cellsociety.CornerLocationGenerator.HexagonalCellCornerLocationGenerator;

import java.util.List;

/**
 * @author Keith Cressman
 */
public class ViewResourceHandlerTest {
  private ViewResourceHandler handler;

  @BeforeEach
  void setup(){
    handler = new ViewResourceHandler();
  }

  @Test
  void testSimWidth(){
    assertEquals(400, handler.simulationWidth());
  }

  @Test
  void testWindowWidth(){
    assertEquals(800, handler.getWindowWidth());
  }

  @Test
  void testWindowHeight(){
    assertEquals(600, handler.getWindowHeight());
  }

  @Test
  void testGameOfLifeColors(){
    Color[] gameOfLifeColors = handler.getColorsForSimulation("GameOfLife");
    assertEquals(gameOfLifeColors[0], Color.web("#000000"));
    assertEquals(gameOfLifeColors[1], Color.web("#FF0000"));
  }

  @Test
  void testSpreadingOfFireColors(){
    Color[] spreadOfFireColors = handler.getColorsForSimulation("SpreadingOfFire");
    assertEquals(spreadOfFireColors[0], Color.web("#000000"));
    assertEquals(spreadOfFireColors[1], Color.web("#FF0000"));
    assertEquals(spreadOfFireColors[2], Color.web("#00FF00"));
  }

  @Test
  void testPercolatoinColors(){
    Color[] percolationColors = handler.getColorsForSimulation("Percolation");
    assertEquals(percolationColors[0], Color.web("#000000"));
    assertEquals(percolationColors[1], Color.web("#FF0000"));
    assertEquals(percolationColors[2], Color.web("#00FF00"));
  }

  @Test
  void testSegregationColors(){
    Color[] segregationColors = handler.getColorsForSimulation("Segregation");
    assertEquals(segregationColors[0], Color.web("#000000"));
    assertEquals(segregationColors[1], Color.web("#FF0000"));
    assertEquals(segregationColors[2], Color.web("#00FF00"));
  }

  @Test
  void testPredatorPreyColors(){
    Color[] pColors = handler.getColorsForSimulation("PredatorPrey");
    assertEquals(pColors[0], Color.web("#000000"));
    assertEquals(pColors[1], Color.web("#FF0000"));
    assertEquals(pColors[2], Color.web("#00FF00"));
  }

  @Test
  void testHistogramBarHeight(){
    int barHeight = handler.getViewSettingValueFromKey(ViewResourceHandler.MAX_HISTOGRAM_BAR_HEIGHT_KEY);
    assertEquals(200, barHeight);
  }

  @Test
  void testHistogramWidth(){
    int width = handler.getViewSettingValueFromKey(ViewResourceHandler.HISTOGRAM_WIDTH_KEY);
    assertEquals(200, width);
  }

  @Test
  void testGetCornerLocationGeneratorRectangular(){
    CornerLocationGenerator clg = handler.getCornerLocationGenerator("Rectangle", 10, 10);
    assertInstanceOf(RectangleCellCornerLocationGenerator.class, clg);
  }

  @Test
  void testGetCornerLocationGeneratorTriangular(){
    CornerLocationGenerator clg = handler.getCornerLocationGenerator("Triangle", 10, 10);
    assertInstanceOf(TriangularCellCornerLocationGenerator.class, clg);
  }

  @Test
  void testGetCornerLocationGeneratorHexagonal(){
    CornerLocationGenerator clg = handler.getCornerLocationGenerator("Hexagon", 10, 10);
    assertInstanceOf(HexagonalCellCornerLocationGenerator.class, clg);
  }

  @Test
  void testShapesList(){
    List<String> shapes = handler.getCellShapes();
    assertEquals(3, shapes.size());
    assertTrue(shapes.contains("Triangle"));
    assertTrue(shapes.contains("Rectangle"));
    assertTrue(shapes.contains("Hexagon"));

  }
}
