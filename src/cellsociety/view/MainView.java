package cellsociety.view;

import cellsociety.Model.Model;
import cellsociety.controller.Controller;
import cellsociety.resourceHandlers.LanguageResourceHandler;

import java.io.File;
import java.io.FileNotFoundException;
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


public class MainView {
  private Controller myController;
  private Model myModel;
  private Stage myStage;
  private LanguageResourceHandler myResourceHandler;
  private Map<NodeWithText, String> myNodesToTextKey;


  private final String[] supportedLanguages = LanguageResourceHandler.SUPPORTED_LANGUAGES;
  private final int DEFAULT_SIM_WIDTH = 400;
  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;

  public MainView(){
    myController = null;
    myModel = null;
    myResourceHandler = new LanguageResourceHandler();
    myNodesToTextKey = new HashMap<>();
  }

  public void setMyController(Controller myController) {
    this.myController = myController;
  }

  public void setMyModel(Model myModel) {
    this.myModel = myModel;
  }

  /**
   * change what myStage refers to
   * @param s is a JavaFX stage on which the scene should be nested
   */
  public void setStage(Stage s){
    myStage = s;
  }

  /**
   * make a scene for this simulation
   * @return a Scene with all the components needed for this simulation
   */
  public Scene makeSimulationScene(){
    Pane mainPane = new VBox();
    mainPane.getChildren().add(makeControlPanel());
    mainPane.getChildren().add(makeFileInputPanel());

    return new Scene(mainPane, WIDTH, HEIGHT);
  }

  private Button makeAButton(String ResourceKey, ButtonClickedMethod method){
    Button2 button = new Button2(myResourceHandler.getStringFromKey(ResourceKey));
    button.setOnAction(e -> method.actionOnClick());
    myNodesToTextKey.put(button, ResourceKey);
    return button;
  }

  private Label makeALabel(String resourceKey){
    Label2 l = new Label2(myResourceHandler.getStringFromKey(resourceKey));
    myNodesToTextKey.put(l, resourceKey);
    return l;
  }
  /**
   * Make a panel with GUI components to change the settings, including
   * fonts, colors, language, and more.
   * @return a Node with components to change/view settings
   */
  private Node makeControlPanel(){
    HBox controlBox = new HBox();
    controlBox.getChildren().add(makeALabel(LanguageResourceHandler.SETTINGS_KEY));
    controlBox.getChildren().add(makeAButton(LanguageResourceHandler.ABOUT_KEY, () -> showAbout()));
    controlBox.getChildren().add(makeLanguageSelector());

    return controlBox;
  }


  /**
   * make a combo Box with which users can select the language on screen
   * @return a Node (for now, a comboBox) to assist users with changing the language
   */
  private Node makeLanguageSelector(){
    HBox languageSelector = new HBox();
    languageSelector.getChildren().add(makeALabel(LanguageResourceHandler.LANGUAGE_KEY));

    languageSelector.getChildren().add(makeAButton(LanguageResourceHandler.CHANGE_ENGLISH_KEY, () -> changeToEnglish()));
    languageSelector.getChildren().add(makeAButton(LanguageResourceHandler.CHANGE_SPANISH_KEY, () -> changeToSpanish()));

    return languageSelector;
  }

  /**
   * change the language of everything on screen to english
   */
  private void changeToEnglish(){
    myResourceHandler.changeToEnglish();
    changeLanguageOfText();
  }

  /**
   * change the language of everything on screen to spanish
   */
  private void changeToSpanish(){
    myResourceHandler.changeToSpanish();
    changeLanguageOfText();
  }


  /**
   * go through each node with text and change it to the new language
   */
  private void changeLanguageOfText(){
    for (NodeWithText n: myNodesToTextKey.keySet()){
      String resourceKey = myNodesToTextKey.get(n);
      n.setText(myResourceHandler.getStringFromKey(resourceKey));
    }
  }

  /**
   * Make a panel with which users can input a new file, or start the simulation
   * @return a Node with components that allow users to add more simulations to the scene
   */
  private Node makeFileInputPanel(){
    Pane fileInputPanel = new HBox();
    fileInputPanel.getChildren().add(makeAButton(LanguageResourceHandler.SELECT_FILE_KEY, () -> selectFile()));
    fileInputPanel.getChildren().add(makeAButton(LanguageResourceHandler.START_SIMULATIONS_KEY, () -> startSimulations()));

    return fileInputPanel;
  }

  /**
   * pop up a box on the GUI that allows a user to select/input a file
   */
  private void selectFile(){
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(myResourceHandler.getSelectFileTitleString());
    fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("SIM Files", "*.sim"));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    handleSelectedFile(selectedFile);
  }

  private void handleSelectedFile(File selectedFile){
    //take the file inputted by a user, and send it to the controller for parsing. Show error messages if neccessary
    try{
      myController.parseFile(selectedFile);
    } catch (Exception e){
      //should handle files with bad formatting, and other such problems
    }
  }

  /**
   * When a user inputs a file, this will be called.
   * This method will use the controller to create a new grid corresponding to the file (if it's a valid file).
   * Note: we can have multiple simulations
   */
  private void addASimulation(){
    //call on controller to parse the file and set up a grid
    System.out.println("Add a simulation");
  }

  /**
   * this method will start all the simulations on the screen
   */
  private void startSimulations(){
    System.out.println("Start simulation");
  }

  /**
   * when the user presses the about button, show them a dialogue box with info about the simulations
   */
  private void showAbout(){
    System.out.println("about");
  }


  /**
   * return the text that should be on this node
   * @param n is a node with text that we'd like to check
   * @return the text that should be on it
   */
  public String getExpectedTextforNode(NodeWithText n){
    String ret = myResourceHandler.getStringFromKey(myNodesToTextKey.get(n));
    return ret;
  }

  /**
   * This is only used for testing, and should probably be edited so that it isn't public, or returns a copy
   * @return myResourceHandler
   */
  public LanguageResourceHandler getMyResourceHandler(){
    return myResourceHandler;
  }







}
