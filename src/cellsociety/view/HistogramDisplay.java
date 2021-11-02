package cellsociety.view;


import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.CSSidHandler;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import javafx.scene.shape.Rectangle;

import javafx.scene.shape.Line;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import cellsociety.resourceHandlers.ViewResourceHandler;



/**
 * This class is here to represent a histogram display, as part of the changes
 * @author Keith Cressman
 */
public class HistogramDisplay extends ChangeableDisplay{
  private int maxNumCells;
  private int maxBarHeight;
  private int zeroBarY;
  private Map<Integer, Rectangle> stateToBar;
  private ViewResourceHandler viewSettings;

  /**
   * Initialize a Histogram display
   * @param numCells is the total number of cells in the simulation
   * @param stateToCount maps each state to the number of cells in that state. It must contain a key for each possible state!
   */
  public HistogramDisplay(int numCells, Map<Integer, Integer> stateToCount, LanguageResourceHandler l){
    super(l);
    initializeSettings();
    maxNumCells = numCells;
    stateToBar = new HashMap<>();
    initializeRectangles(stateToCount);
    setNumOfEachType(stateToCount);
  }

  private void initializeSettings(){
    //set up the settings for parameters in the display
    viewSettings = new ViewResourceHandler();
    maxBarHeight = viewSettings.getViewSettingValueFromKey(ViewResourceHandler.MAX_HISTOGRAM_BAR_HEIGHT_KEY);
    zeroBarY = viewSettings.getViewSettingValueFromKey(ViewResourceHandler.ZERO_BAR_Y_KEY);
  }

  private void initializeRectangles(Map<Integer, Integer> stateToCount){
    //create the bar/rectangle for each state in the histogram
    int startX = viewSettings.getViewSettingValueFromKey(ViewResourceHandler.DIST_BTWN_HISTOGRAM_BARS_KEY);
    int barWidth = viewSettings.getViewSettingValueFromKey(ViewResourceHandler.HISTOGRAM_WIDTH_KEY) / (2* stateToCount.size());
    for (Integer state: stateToCount.keySet()){
      stateToBar.put(state, new Rectangle(startX, zeroBarY, barWidth, 0));
      stateToBar.get(state).setLayoutX(startX);

      startX = startX + barWidth + viewSettings.getViewSettingValueFromKey(ViewResourceHandler.DIST_BTWN_HISTOGRAM_BARS_KEY);
    }

  }

  /**
   * Create the display for this histogram. This object must have been initialized first!
   * @return the Node representing the display of the histogram
   */
  public Node createHistogramDisplay(){
    Group container = new Group();
    Rectangle backGround = new Rectangle(0, 0, viewSettings.getViewSettingValueFromKey(ViewResourceHandler.HISTOGRAM_WIDTH_KEY),
        viewSettings.getViewSettingValueFromKey(ViewResourceHandler.HISTOGRAM_HEIGHT_KEY));
    backGround.setId((new CSSidHandler()).getStringFromKey(CSSidHandler.HISTOGRAM_BACKGROUND_KEY));

    container.getChildren().add(backGround);
    container.getChildren().add(makeALabel(LanguageResourceHandler.HISTOGRAM_TITLE_KEY));
    container.getChildren().add(new Line(0,zeroBarY, viewSettings.getViewSettingValueFromKey(ViewResourceHandler.HISTOGRAM_WIDTH_KEY), zeroBarY));
    addRectanglesToDisplay(container);
    myDisp = container;
    return container;
  }

  private void addRectanglesToDisplay(Group container){
    //add the rectangles (bars) to the display
    for (Integer state : stateToBar.keySet()){
      Rectangle rect = stateToBar.get(state);
      container.getChildren().add(rect);
      Label stateLabel = new Label(Integer.toString(state));
      stateLabel.setLayoutY(zeroBarY);
      stateLabel.setLayoutX(rect.getLayoutX());
      container.getChildren().add(stateLabel);
    }
  }

  /**
   * Set how many cells there are of each type
   * @param stateToCount maps a cell type to the number of cells of that type (e.g. {0: 5, 1: 10, 2: 1}
   */
  public void setNumOfEachType(Map<Integer, Integer> stateToCount){
    for (Integer state : stateToCount.keySet()){
      changeBarHeight(state, stateToCount.get(state));
    }
  }

  /**
   * use this method to change the height of a bar on the histogram, to reflect the number of cells of a given state
   * @param state is the state of the bar whose height we wish to change
   * @param count is the number of cells of that state
   */
  private void changeBarHeight(int state, int count){
    double fraction = ((double) count) / ((double)maxNumCells);
    double height = fraction * maxBarHeight;

    Rectangle stateBar = stateToBar.get(state);
    stateBar.setLayoutY(-1*height); //this is weird, maybe look into later if we have time
    stateBar.setHeight(height);
  }

  /**
   * This is used for testing purposes
   * @return a map, mapping each state (integer) to its rectangle on the histogram
   */
  public Map<Integer, Rectangle> getHistogramBars(){
    return Collections.unmodifiableMap(stateToBar);
  }
}
