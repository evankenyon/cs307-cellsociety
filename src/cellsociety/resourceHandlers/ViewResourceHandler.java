package cellsociety.resourceHandlers;

import java.util.Properties;
import java.util.ResourceBundle;
import java.io.FileInputStream;


public class ViewResourceHandler {
  private ResourceBundle myResourceBundle;
  private Properties viewProperties;
  private final String DISPLAY_PROPS_PATH = "src/cellsociety/resourceHandlers/DisplayProperties.properties";
  private final String WIDTH_KEY = "SimulationWidth";


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
    return Integer.parseInt(viewProperties.getProperty(WIDTH_KEY));
  }
}
