package resourceHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import cellsociety.resourceHandlers.CSSidHandler;

public class CSSidHandlerTest {
  private CSSidHandler myHandler;

  @BeforeEach
  void setup(){
    myHandler = new CSSidHandler();
  }

  @Test
  void testHistogramBackgroundId(){
    String actual = myHandler.getStringFromKey(CSSidHandler.HISTOGRAM_BACKGROUND_KEY);
    assertEquals("HistogramBackground", actual);
  }
}
