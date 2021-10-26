package cellsociety.resourceHandlers;

import java.util.Properties;
import java.util.ResourceBundle;
import java.io.FileInputStream;


public class ViewResourceHandler {
  private ResourceBundle myResourceBundle;
  private Properties viewProperties;
  private final String DISPLAY_PROPS_PATH = "src/cellsociety/resourceHandlers/DisplayProperties.properties";
  private final String SIM_WIDTH_KEY = "SimulationWidth";
  private final String WINDOW_WIDTH_KEY = "windowWidth";
  private final String WINDOW_HEIGHT_KEY = "windowHeight";



  public ViewResourceHandler() {
    viewProperties = new Properties();
    try {
      viewProperties.load(new FileInputStream(DISPLAY_PROPS_PATH));
    } catch(Exception e){
      //impossible
    }
  }

  /**
   * get the width of the simulation area
   * @return the pixel width of the simulation area
   */
  public int simulationWidth(){
    return Integer.parseInt(viewProperties.getProperty(SIM_WIDTH_KEY));
  }

  /**
   * get wdith of window
   * @return width of window in pixels
   */
  public int getWindowWidth(){
      return Integer.parseInt(viewProperties.getProperty(WINDOW_WIDTH_KEY));
  }


  /**
   * get height of window
   * @return height of window in pixels
   */
  public int getWindowHeight(){
    return Integer.parseInt(viewProperties.getProperty(WINDOW_HEIGHT_KEY));
  }

}
