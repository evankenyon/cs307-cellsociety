package cellsociety.view;

import cellsociety.Model.Model;
import cellsociety.controller.Controller;
import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.ViewResourceHandler;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.application.Platform;


import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;



public class MainView extends ChangeableDisplay{

  private Stage myStage;
  private LanguageResourceHandler myResourceHandler;
  private ViewResourceHandler myViewResourceHandler;

  private Map<NodeWithText, String> myNodesToTextKey;
  private List<SimulationDisplay> simulationDisplayList;
  private Pane mainPane;


  public static final String DARK_CSS_NAME = "DarkMode.css";
  public static final String DUKE_CSS_NAME = "Duke.css";
  public static final String BASIC_CSS_NAME = "Basic.css";

  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;

  public MainView(){
    super();
    myResourceHandler = new LanguageResourceHandler();
    myViewResourceHandler = new ViewResourceHandler();
    myNodesToTextKey = new HashMap<>();
    simulationDisplayList = new ArrayList<>();
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
    mainPane = new VBox();
    mainPane.getChildren().add(makeALabel(LanguageResourceHandler.SETTINGS_KEY));
    mainPane.getChildren().add(makeLanguageSelector());
    mainPane.getChildren().add(makeStyleSelector());
    mainPane.getChildren().add(makeFileInputPanel());
    Scene s = new Scene(mainPane, myViewResourceHandler.getWindowWidth(),
        myViewResourceHandler.getWindowHeight());
    s.getStylesheets().add(getClass().getResource(BASIC_CSS_NAME).toExternalForm());
    return s;
  }


  private void makeNewWindow(){
    //create a new window to run another simulation
    Stage s = new Stage();
    MainView mv = new MainView();
    s.setScene(mv.makeSimulationScene());
    mv.setStage(s);
    s.show();
  }

  private Node makeLanguageSelector(){
    //make a combo box with which users can select the language on screen
    HBox languageSelector = new HBox();
    languageSelector.getChildren().add(makeALabel(LanguageResourceHandler.LANGUAGE_KEY));

    languageSelector.getChildren().add(makeAButton(LanguageResourceHandler.CHANGE_ENGLISH_KEY, () -> changeToEnglish()));
    languageSelector.getChildren().add(makeAButton(LanguageResourceHandler.CHANGE_SPANISH_KEY, () -> changeToSpanish()));

    return languageSelector;
  }

  private Node makeStyleSelector(){
    //make GUI component allowing user to choose between dark mode, Duke colors, basic style, etc..
    HBox styleBox = new HBox();
    styleBox.getChildren().add(makeALabel(LanguageResourceHandler.STYLE_SELECTOR_KEY));
    styleBox.getChildren().add(makeAButton(LanguageResourceHandler.DARK_MODE_KEY, () -> changeStyle(DARK_CSS_NAME)));
    styleBox.getChildren().add(makeAButton(LanguageResourceHandler.DUKE_MODE_KEY, () -> changeStyle(DUKE_CSS_NAME)));
    styleBox.getChildren().add(makeAButton(LanguageResourceHandler.BASIC_MODE_KEY, () -> changeStyle(BASIC_CSS_NAME)));
    return styleBox;
  }

  private void changeStyle(String newStyle){
    //change the css styling
    System.out.println(newStyle);
    myStage.getScene().getStylesheets().clear();
    myStage.getScene().getStylesheets().add(getClass().getResource(newStyle).toExternalForm());
  }




  private Node makeFileInputPanel(){
    //make a panel with which users can input a new file
    Pane fileInputPanel = new HBox();
    fileInputPanel.getChildren().add(makeAButton(LanguageResourceHandler.SELECT_FILE_KEY, () -> selectFile()));
    fileInputPanel.getChildren().add(makeAButton(LanguageResourceHandler.NEW_SIMULATION_KEY, () -> makeNewWindow()));

    return fileInputPanel;
  }


  private void selectFile(){
    //pop up a box on the GUI that allows a user to select a file
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/game_of_life"));
    fileChooser.setTitle(myResourceHandler.getSelectFileTitleString());
    fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("SIM Files", "*.sim"));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    handleSelectedFile(selectedFile);
  }

  /**
   * handle a file that has been inputted
   * @param selectedFile
   */
  public void handleSelectedFile(File selectedFile){
    //take the file inputted by a user, and send it to the controller for parsing. Show error messages if neccessary
    SimulationDisplay simDisp = new SimulationDisplay(myLanguageResourceHandler);
    simulationDisplayList.add(simDisp);
    try{
      addSimulationDisplay(simDisp.makeDisplay(selectedFile));
    } catch (Exception e){
      displayErrorMessage(e.getMessage());
    }
  }



  private void addSimulationDisplay(Node newDisplay) throws Exception{
    //add display for a new simulation. This will be more complicated if there are multiple on screen at once
    Platform.runLater(new Runnable() {
      @Override public void run() {
        mainPane.getChildren().add(newDisplay);
      }
    });
  }


  /**
   * go through each node with text and change it to the new language
   */
  protected void changeLanguageOfText(){
    super.changeLanguageOfText();
    for (SimulationDisplay simDisp : simulationDisplayList){
      simDisp.changeLanguageOfText();
    }
  }

  /**
   * get the number of simulations being run. This is for testing purposes
   * @return the number of simulations being run
   */
  public int getNumSimulations(){
    return simulationDisplayList.size();
  }

  /**
   * get the simulation display. Used only for testing
   * @return the simulation display
   */
  public SimulationDisplay getSimDisplay(){
    if (simulationDisplayList.size() > 0) {
      return simulationDisplayList.get(0);
    } else{
      return null;
    }
  }





}
