package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import java.util.Map;
import java.util.HashMap;

/**
 * This class represents a display aspect with text that can be displayed in another language,
 * and with components whose styling can change.
 * It is extnded by MainView and SimulationDisplay, since those classes are used to represent display
 * components that can be displayed in another language/style.
 * @author Keith Cressman
 */
public abstract class ChangeableDisplay {
  protected LanguageResourceHandler myLanguageResourceHandler;
  private Map<NodeWithText, String> myNodesToTextKey;


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

}
