package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.CSSidHandler;
import cellsociety.controller.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * objects of this class are used to create a pop up dialogue box which a user can interact with to
 * save a .sim file and .csv file corresponding to the current simulation
 * <p>
 * To do: actually save file, and handle changing to dark mode / spanish
 *
 * @author Keith Cressman
 */
public class FileSavePopup extends ChangeableDisplay {

  public static final String INITIAL_STATES = "InitialStates";
  public static final String TYPE = "Type";
  public static final String TITLE = "Title";
  public static final String AUTHOR = "Author";
  public static final String DESCRIPTION = "Description";
  public static final String OTHER = "";
  public final static String DIRECTORY_PROPERTIES_PATH = "src/cellsociety/Utilities/DataDirectory.properties";

  private TextField typeField, titleField, authorField, descriptionField, fileNameField;
  private TextArea otherArea;
  private Controller myController;
  private Stage myStage;

  public FileSavePopup(LanguageResourceHandler lrh, Controller controller) {
    typeField = new TextField();
    titleField = new TextField();
    authorField = new TextField();
    descriptionField = new TextField();
    fileNameField = new TextField();
    fileNameField.setId((new CSSidHandler()).getStringFromKey(CSSidHandler.FILENAME_SAVE_KEY));

    myLanguageResourceHandler = lrh;
    myController = controller;
  }

  /**
   * make a popup which a user can follow to save their file
   */
  public void makePopup() {

    myStage = new Stage();
    Scene scene = new Scene(makePopupContent());
    myStage.setScene(scene);
    myStage.show();
  }

  private Pane makePopupContent() {
    //set the content for the popup
    VBox content = new VBox();
    content.getChildren().addAll(makeTextFields());
    content.getChildren().add(makeOtherBox());
    content.getChildren()
        .add(makeAButton(LanguageResourceHandler.SAVE_BUTTON_POPUP_KEY, () -> saveFile()));
    return content;
  }

  private List<Node> makeTextFields() {
    //make the list of text fields and their corresponding labels
    List<Node> nodeList = new ArrayList<>();
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_TYPE_SAVE_KEY, typeField));
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_TITLE_SAVE_KEY, titleField));
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_AUTHOR_SAVE_KEY, authorField));
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_DESCRIPTION_SAVE_KEY, descriptionField));
    nodeList.add(makeSaveNode(LanguageResourceHandler.SIM_FILENAME_SAVE_KEY, fileNameField));
    return nodeList;
  }

  private Node makeOtherBox() {
    //make the Pane with a text area and label for the other field of the .sim file
    HBox otherBox = new HBox();
    otherBox.getChildren().add(makeALabel(LanguageResourceHandler.SIM_OTHER_SAVE_KEY));
    otherArea = new TextArea();
    otherBox.getChildren().add(otherArea);
    return otherBox;
  }


  private void saveFile() {
    //use controller to save a .sim and .csv file
    Map<String, String> propertyToValue = new HashMap<>();

    propertyToValue.put(INITIAL_STATES, makeFilePath());
    propertyToValue.put(TYPE, typeField.getText());
    propertyToValue.put(TITLE, titleField.getText());
    propertyToValue.put(AUTHOR, authorField.getText());
    propertyToValue.put(DESCRIPTION, descriptionField.getText());
    propertyToValue.put(OTHER, otherArea.getText());
    try {
      myController.saveFile(fileNameField.getText(), propertyToValue);
    } catch (IOException e) {
      displayErrorMessage(
          myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.FAILED_SAVE_KEY));
    }
    myStage.close();

  }

  private String makeFilePath() {
    //get the path where our files should be saved to
    String fileName = fileNameField.getText();
    Properties pathProperties = new Properties();
    try {
      pathProperties.load(new FileInputStream(DIRECTORY_PROPERTIES_PATH));
    } catch (IOException e) {
      displayErrorMessage(
          myLanguageResourceHandler.getStringFromKey(LanguageResourceHandler.FAILED_SAVE_KEY));
    }
    String folder = pathProperties.getProperty(myController.getSimulationType());
    String filePath = String.format("./data/" + folder + "/saved/program-" + "%s.sim", fileName);
    return filePath;
  }

  private Node makeSaveNode(String labelText, TextField tField) {
    //make a node with a label and text field
    HBox titleBox = new HBox();
    titleBox.getChildren().add(makeALabel(labelText));
    titleBox.getChildren().add(tField);
    return titleBox;
  }

}
