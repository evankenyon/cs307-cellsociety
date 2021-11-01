package cellsociety.resourceHandlers;

import java.util.Properties;
import java.util.ResourceBundle;
import java.io.FileInputStream;
import javafx.scene.paint.Color;


/**
 * Objects of this class handle property files relating to the view
 * @author Keith Cressman
 */
public class ViewResourceHandler {
  private ResourceBundle myResourceBundle;
  private Properties viewProperties, stateColorProperties;
  private static final String DISPLAY_PROPS_PATH = "src/cellsociety/resourceHandlers/DisplayProperties.properties";
  private static final String STATE_COLORS_PATH = "src/cellsociety/resourceHandlers/StateColor.properties";
  private static final String SIM_WIDTH_KEY = "SimulationWidth";
  private static final String SIM_HEIGHT_KEY = "SimulationHeight";
  private static final String WINDOW_WIDTH_KEY = "windowWidth";
  private static final String WINDOW_HEIGHT_KEY = "windowHeight";
  private static final String MIN_FRAMES_KEY = "MinFramesPerSecond";
  private static final String MAX_FRAMES_KEY = "MaxFramesPerSecond";


  public ViewResourceHandler() {
    viewProperties = new Properties();
    stateColorProperties = new Properties();
    try {
      viewProperties.load(new FileInputStream(DISPLAY_PROPS_PATH));
      stateColorProperties.load(new FileInputStream(STATE_COLORS_PATH));
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
   * get the height of the simulation area
   * @return the pixel height of the simulation area
   */
  public int simulationHeight(){
    return Integer.parseInt(viewProperties.getProperty(SIM_HEIGHT_KEY));
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

  /**
   * get the mininum frames per second for a simulation
   * @return minimum frames per second
   */
  public int getMinFramesPerSecond(){
    return Integer.parseInt(viewProperties.getProperty(MIN_FRAMES_KEY));
  }


  /**
   * get the max frames per second for a simulation
   * @return maximum frames per second
   */
  public int getMaxFramesPerSecond(){
    return Integer.parseInt(viewProperties.getProperty(MAX_FRAMES_KEY));
  }

  /**
   * get an array of the colors for the specified simultion type
   * @param simulationType is the type of simulation in question
   * @return an array where the ith Color corresponds to the color for state i in the the simulation
   */
  public Color[] getColorsForSimulation(String simulationType){
    String colorsFromProps = stateColorProperties.getProperty(simulationType);
    String[] colorStrings = colorsFromProps.split(" ");
    Color[] colors = new Color[colorStrings.length];
    for (int i = 0; i < colorStrings.length; i++){
      colors[i] = Color.web(colorStrings[i]);
    }
    return colors;
  }



}
