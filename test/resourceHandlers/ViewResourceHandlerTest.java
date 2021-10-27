package resourceHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import cellsociety.resourceHandlers.ViewResourceHandler;

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
}
