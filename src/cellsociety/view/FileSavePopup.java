package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.ViewResourceHandler;
import cellsociety.controller.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * objects of this class are used to create a pop up dialogue box which a user can interact with
 * to save a .sim file and .csv file corresponding to the current simulation
 *
 * To do: actually save file, and handle changing to dark mode / spanish
 * @author Keith Cressman
 */
public class FileSavePopup extends ChangeableDisplay{

  public static final String INITIAL_PROPERTIES = "InitialProperties";
  public static final String TYPE = "Type";
  public static final String TITLE = "Title";
  public static final String AUTHOR = "Author";
  public static final String DESCRIPTION = "Description";
  public static final String OTHER = "";

  private TextField typeField, titleField, authorField, descriptionField, fileNameField;
  private TextArea otherArea;
  private Controller myController;

  public FileSavePopup(LanguageResourceHandler lrh, Controller controller){
    typeField = new TextField();
    titleField = new TextField();
    authorField = new TextField();
    descriptionField = new TextField();
    fileNameField = new TextField();

    myLanguageResourceHandler = lrh;
    myController = controller;
  }

  /**
   * make a popup which a user can follow to save their file
   */
  public void makePopup(){

    Stage stage = new Stage();
    Scene scene = new Scene(makePopupContent());
    stage.setScene(scene);
    stage.show();
  }

  private Pane makePopupContent(){
    //set the content for the popup
    VBox content = new VBox();
    content.getChildren().addAll(makeTextFields());
    content.getChildren().add(makeOtherBox());
    content.getChildren().add(makeAButton(LanguageResourceHandler.SAVE_BUTTON_POPUP_KEY, () -> saveFile()));
    return content;
  }

  private List<Node> makeTextFields(){
    //make the list of text fields and their corresponding labels
    List<Node> nodeList = new ArrayList<>();
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_TYPE_SAVE_KEY, typeField));
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_TITLE_SAVE_KEY, titleField));
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_AUTHOR_SAVE_KEY, authorField));
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_DESCRIPTION_SAVE_KEY, descriptionField));
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_FILENAME_SAVE_KEY, fileNameField));
    return nodeList;
  }

  private Node makeOtherBox(){
    //make the Pane with a text area and label for the other field of the .sim file
    HBox otherBox = new HBox();
    otherBox.getChildren().add(makeALabel(LanguageResourceHandler.SIM_OTHER_SAVE_KEY));
    otherArea = new TextArea();
    otherBox.getChildren().add(otherArea);
    return otherBox;
  }


  private void saveFile(){
    //use controller to save a .sim and .csv file
    Map<String, String> propertyToValue = new HashMap<>();
    propertyToValue.put(INITIAL_PROPERTIES, fileNameField.getText()); //need to add full path
    propertyToValue.put(TYPE, typeField.getText());
    propertyToValue.put(TITLE, titleField.getText());
    propertyToValue.put(AUTHOR, authorField.getText());
    propertyToValue.put(DESCRIPTION, descriptionField.getText());
    propertyToValue.put(OTHER, otherArea.getText());

  }

  private Node makeSaveNode(String labelText, TextField tField){
    //make a node with a label and text field
    HBox titleBox = new HBox();
    titleBox.getChildren().add(makeALabel(labelText));
    titleBox.getChildren().add(tField);
    return titleBox;
  }

}
