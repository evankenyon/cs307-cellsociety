package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;

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


public class SimulationView {
  //private Controller myController;
  private Stage myStage;
  private LanguageResourceHandler myResourceHandler;
  private Map<NodeWithText, String> myNodesToTextKey;


  //the following constants should go into resources
  private final String SETTINGS_STRING = "Settings";
  private final String ABOUT_STRING = "About";
  private final String LANGUAGE_STRING = "Choose Language";
  private final String SELECT_FILE = "Select a file";
  private final String START_SIMULATIONS = "Start Simulations";
  private final String[] supportedLanguages = LanguageResourceHandler.SUPPORTED_LANGUAGES;
  private final int DEFAULT_SIM_WIDTH = 400;

  public SimulationView(){
    //myController = new Controller()
    myResourceHandler = new LanguageResourceHandler();
    myNodesToTextKey = new HashMap<>();
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

    return new Scene(mainPane, MainView.WIDTH, MainView.HEIGHT);
  }

  private Button makeAButton(String ResourceKey, ButtonClickedMethod method){
    Button2 button = new Button2(myResourceHandler.getStringFromKey(ResourceKey));
    button.setOnAction(e -> method.actionOnClick());
    myNodesToTextKey.put(button, ResourceKey);
    return button;
  }
  /**
   * Make a panel with GUI components to change the settings, including
   * fonts, colors, language, and more.
   * @return a Node with components to change/view settings
   */
  private Node makeControlPanel(){
    HBox controlBox = new HBox();
    Label2 settingsLabel = new Label2(myResourceHandler.getSettingsString());
    myNodesToTextKey.put(settingsLabel, LanguageResourceHandler.SETTINGS_KEY);
    controlBox.getChildren().add(settingsLabel);
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
    Label2 languageLabel = new Label2(myResourceHandler.getLanguageString());
    myNodesToTextKey.put(languageLabel, LanguageResourceHandler.LANGUAGE_KEY);
    languageSelector.getChildren().add(languageLabel);

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
    System.out.println("Select a file");
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







}
