package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;

import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import java.util.HashMap;


/**
 * This class represents a display aspect with text that can be displayed in another language,
 * and with components whose styling can change.
 * It is extnded by MainView, SimulationDisplay, CellGridDisplay, HistogramDisplay, and InfoDisplay
 * since those classes are used to represent display components that can be displayed in another language/style.
 * I maybe could've split up the button/label creating functionality into a separate class -Keith
 * @author Keith Cressman
 */
public abstract class ChangeableDisplay {
  protected LanguageResourceHandler myLanguageResourceHandler;
  private Map<NodeWithText, String> myNodesToTextKey;
  protected Node myDisp;


  public ChangeableDisplay(){
    this(new LanguageResourceHandler());
  }

  public ChangeableDisplay(LanguageResourceHandler l){
    myLanguageResourceHandler = l;
    myNodesToTextKey = new HashMap<>();
  }

  protected Button makeAButton(String ResourceKey, ButtonClickedMethod method){
    Button2 button = new Button2(myLanguageResourceHandler.getStringFromKey(ResourceKey));
    button.setOnAction(e -> method.actionOnClick());
    myNodesToTextKey.put(button, ResourceKey);
    return button;
  }

  protected Label makeALabel(String resourceKey){
    Label2 l = new Label2(myLanguageResourceHandler.getStringFromKey(resourceKey));
    myNodesToTextKey.put(l, resourceKey);
    return l;
  }

  /**
   * change the language of everything on screen to english
   */
  protected void changeToEnglish(){
    myLanguageResourceHandler.changeToEnglish();
    changeLanguageOfText();
  }

  /**
   * change the language of everything on screen to spanish
   */
  protected void changeToSpanish(){
    myLanguageResourceHandler.changeToSpanish();
    changeLanguageOfText();
  }


  /**
   * go through each node with text and change it to the new language
   */
  protected void changeLanguageOfText(){
    for (NodeWithText n: myNodesToTextKey.keySet()){
      String resourceKey = myNodesToTextKey.get(n);
      n.setText(myLanguageResourceHandler.getStringFromKey(resourceKey));
    }
  }

  /**
   * return the text that should be on this node
   * @param n is a node with text that we'd like to check
   * @return the text that should be on it
   */
  public String getExpectedTextforNode(NodeWithText n){
    String ret = myLanguageResourceHandler.getStringFromKey(myNodesToTextKey.get(n));
    return ret;
  }

  /**
   * This is only used for testing, and should probably be edited so that it isn't public, or returns a copy
   * @return myResourceHandler
   */
  public LanguageResourceHandler getMyResourceHandler(){
    return myLanguageResourceHandler;
  }

  /**
   * pop up a box with an error message on it
   * @param message will be displayed on the GUI
   */
  protected void displayErrorMessage(String message){
    Alert a = new Alert(AlertType.WARNING);
    a.setContentText(message);
    a.show();
  }

  /**
   * set the display visible/invisible, or display an error message if myDisp is null, which is only possible
   * if the code is being misused.
   * @param visible should be true to set the display visible
   */
  public void setVisible(boolean visible){
    try{
      myDisp.setVisible(visible);
    } catch(Exception e){
      displayErrorMessage(e.getMessage());
    }
  }

}
