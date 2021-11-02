package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;


import cellsociety.view.ViewUtilities.Button2;
import cellsociety.view.ViewUtilities.ButtonClickedMethod;
import cellsociety.view.ViewUtilities.Label2;
import cellsociety.view.ViewUtilities.NodeWithText;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.function.Consumer;


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
    myDisp = new Pane();
  }

  /**
   * create a button whose text comes from the key ResourceKey, and calls method when clicked
   * @param ResourceKey is a key to the Properties files in the Language bundle used to get the text on the button
   * @param method will be called when the buton is clicked
   * @return a button with the attributes above
   */
  public Button makeAButton(String ResourceKey, ButtonClickedMethod method){
    Button2 button = new Button2(myLanguageResourceHandler.getStringFromKey(ResourceKey));
    button.setOnAction(e -> method.actionOnClick());
    myNodesToTextKey.put(button, ResourceKey);
    return button;
  }

  /**
   * create a label whose text comes from the key resourceKey
   * @param resourceKey is a key to the Properties files in the Language bundle used to get the text on the label
   * @return a label with the attributes above
   */
  public Label makeALabel(String resourceKey){
    Label2 l = new Label2(myLanguageResourceHandler.getStringFromKey(resourceKey));
    myNodesToTextKey.put(l, resourceKey);
    return l;
  }

  /**
   * Create a Node with a label and a combo box which, when an option is selected, will changeMethod
   * with the comboBox's selected value as the argument to changeMehtod
   * @param labelResourceKey is a key used to get the text on the Label
   * @param options is a list of options to appear on the drop down box
   * @param changeMethod will be called when a new value is selected on the drop down
   * @return a node containing the nodes described above
   */
  public Node makeOptionsBox(String labelResourceKey, Collection<String> options, Consumer<String> changeMethod){
    //create a node on which users can select the shape of a cell
    HBox container = new HBox();
    container.getChildren().add(makeALabel(labelResourceKey));
    ComboBox cBox = new ComboBox();
    cBox.getItems().addAll(options);
    cBox.setOnAction(e -> changeMethod.accept(cBox.getSelectionModel().getSelectedItem().toString()));
    container.getChildren().add(cBox);
    return container;
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
