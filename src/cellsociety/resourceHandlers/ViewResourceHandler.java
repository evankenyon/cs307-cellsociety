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
  public final static String DISPLAY_PROPS_PATH = "src/cellsociety/resourceHandlers/DisplayProperties.properties";
  public final static String STATE_COLORS_PATH = "src/cellsociety/resourceHandlers/StateColor.properties";
  public final static String SIM_WIDTH_KEY = "SimulationWidth";
  public final static String SIM_HEIGHT_KEY = "SimulationHeight";
  public final static String WINDOW_WIDTH_KEY = "windowWidth";
  public final static String WINDOW_HEIGHT_KEY = "windowHeight";
  public final static String MIN_FRAMES_KEY = "MinFramesPerSecond";
  public final static String MAX_FRAMES_KEY = "MaxFramesPerSecond";
  public final static String HISTOGRAM_WIDTH_KEY = "HistogramWidth";
  public final static String HISTOGRAM_HEIGHT_KEY = "HistogramHeight";
  public final static String MAX_HISTOGRAM_BAR_HEIGHT_KEY = "MaxHistogramBarHeight";
  public final static String DIST_BTWN_HISTOGRAM_BARS_KEY = "DistanceBetweenHistogramBars";
  public final static String ZERO_BAR_Y_KEY = "ZeroBarY";


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
   * get the width of the entire node containing the histogram
   * @return histogram width
   */
  public int getHistogramWidth(){
    return Integer.parseInt(viewProperties.getProperty(HISTOGRAM_WIDTH_KEY));
  }




  /**
   * get the maximum height of a bar on the histogram
   * @return histogram bar max height
   */
  public int getMaxHistogramBarHeight(){
    return Integer.parseInt(viewProperties.getProperty(MAX_HISTOGRAM_BAR_HEIGHT_KEY));
  }

  /**
   * get the distance between bars on the histogram
   * @return number of pixels between each bar
   */
  public int getDistanceBetweenHistogramBars(){
    return Integer.parseInt(viewProperties.getProperty(DIST_BTWN_HISTOGRAM_BARS_KEY));
  }

  /**
   * Get the (integer) value associated with the given key
   * @param key is a key in DisplayProperties.properties
   * @return the Integer version of the corresponding value in DisplayProperties
   */
  public int getViewSettingValueFromKey(String key){
    return Integer.parseInt(viewProperties.getProperty(key));
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
