package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import java.util.Collections;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

import java.util.Map;
import java.util.HashMap;

/**
 * Objects of this class represent the third new visualization option in the changes specification.
 * This new visualization involves displaying each state, the image/display for that state, and the
 * change in the last step in the number of cells in that state
 *
 * @author Keith Cressman
 */
public class InfoDisplay extends ChangeableDisplay {

  private Map<Integer, Integer> myStateToCount;
  private Map<Integer, Label> stateToNumChangeLabel;

  /**
   * Create an info Display. The createInfoDisplay method is needed to actually get the node and put
   * it on the scene
   *
   * @param stateToCount maps each state to the number of cells in that state
   */
  public InfoDisplay(Map<Integer, Integer> stateToCount, LanguageResourceHandler l) {
    super(l);
    myStateToCount = stateToCount;
    stateToNumChangeLabel = new HashMap<>();
  }

  /**
   * create the node for this display, which will go onto the scene
   *
   * @return a node containing all the elements of this display
   */
  public Node createInfoDisplay() {
    Pane container = new VBox();
    container.getChildren().add(makeALabel(LanguageResourceHandler.INFO_DISPLAY_TITLE_KEY));
    HBox table = new HBox();
    table.getChildren().add(makeColumnForState());
    table.getChildren().add(makeColumnForName());
    table.getChildren().add(makeColumnForDisplay());
    table.getChildren().add(makeColumnForChange());
    container.getChildren().add(table);
    myDisp = container;
    return container;
  }


  /**
   * note: the following several methods should be refactored because they are very similar
   *
   * @return
   */
  private Node makeColumnForState() {
    //make the column in the "table" for the state
    VBox column = new VBox();
    column.getChildren().add(makeALabel(LanguageResourceHandler.INFO_STATE_KEY));
    for (Integer state : myStateToCount.keySet()) {
      column.getChildren().add(new Label(Integer.toString(state)));
    }
    return column;
  }

  private Node makeColumnForName() {
    //make the column in the "table" for the name of each state
    VBox column = new VBox();
    column.getChildren().add(makeALabel(LanguageResourceHandler.INFO_NAME_KEY));
    return column;
  }

  private Node makeColumnForDisplay() {
    //make the column in the "table" for the display of a cell in each state
    VBox column = new VBox();
    column.getChildren().add(makeALabel(LanguageResourceHandler.INFO_DISPLAY_KEY));
    return column;
  }

  private Node makeColumnForChange() {
    //make the column in the "table" for the state
    VBox column = new VBox();
    column.getChildren().add(makeALabel(LanguageResourceHandler.INFO_CHANGE_KEY));
    for (Integer state : myStateToCount.keySet()) {
      Label numChangeLabel = new Label(Integer.toString(0));
      stateToNumChangeLabel.put(state, numChangeLabel);
      column.getChildren().add(numChangeLabel);
    }
    return column;
  }


  /**
   * Set how many cells there are of each type Note: HistogramDisplay has a very similar method, but
   * I didn't think it was worth it to work this into some sort of inheritance hierarchy, as it
   * would only affect one method in these two classes. If there were like 10 different display
   * options then I would go for it, but it seems like overkill here. -Keith
   *
   * @param stateToCount maps a cell type to the number of cells of that type (e.g. {0: 5, 1: 10, 2:
   *                     1}
   */
  public void setNumOfEachType(Map<Integer, Integer> stateToCount) {
    for (Integer state : stateToCount.keySet()) {
      int diff = stateToCount.get(state) - myStateToCount.get(state);
      stateToNumChangeLabel.get(state).setText(Integer.toString(diff));
    }
    myStateToCount = stateToCount;
  }

  /**
   * get the map of state to number change label. Used for testing
   *
   * @return an unmodifiable version of stateToNumChangeLabel
   */
  public Map<Integer, Label> getStateToNumChangeLabel() {
    return Collections.unmodifiableMap(stateToNumChangeLabel);
  }


}
