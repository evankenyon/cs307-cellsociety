package cellsociety.view;

import cellsociety.resourceHandlers.LanguageResourceHandler;
import cellsociety.resourceHandlers.ViewResourceHandler;
import cellsociety.resourceHandlers.CSSidHandler;

import cellsociety.view.ViewUtilities.NodeWithText;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Objects of this class represent a Simulation window, from which users can select a file (simulation) to run,
 * change language, change style, or add a new window for another simulation
 * @author Keith Cressman
 */
public class MainView extends ChangeableDisplay{

  private Stage myStage;
  private LanguageResourceHandler myResourceHandler;
  private ViewResourceHandler myViewResourceHandler;

  private Map<NodeWithText, String> myNodesToTextKey;
  private SimulationDisplay mySimulationDisplay;
  private Pane mainPane;


  public static final String DARK_CSS_NAME = "CSSFiles/DarkMode.css";
  public static final String DUKE_CSS_NAME = "CSSFiles/Duke.css";
  public static final String BASIC_CSS_NAME = "CSSFiles/Basic.css";
  private Map<String, String> styleNameToPath;


  /**
   * create a main view
   */
  public MainView(){
    super();
    myResourceHandler = new LanguageResourceHandler();
    myViewResourceHandler = new ViewResourceHandler();
    myNodesToTextKey = new HashMap<>();
    mySimulationDisplay = null;
    setUpStyleNameToPathMap();
  }

  private void setUpStyleNameToPathMap(){
    //set up the map that goes from style name to css file path
    styleNameToPath = new HashMap<>();
    styleNameToPath.put(myResourceHandler.getStringFromKey(LanguageResourceHandler.BASIC_MODE_KEY), BASIC_CSS_NAME);
    styleNameToPath.put(myResourceHandler.getStringFromKey(LanguageResourceHandler.DARK_MODE_KEY), DARK_CSS_NAME);
    styleNameToPath.put(myResourceHandler.getStringFromKey(LanguageResourceHandler.DUKE_MODE_KEY), DUKE_CSS_NAME);
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
    Collection<String> languages = myLanguageResourceHandler.getSupportedLanguages();
    Node languageBox = makeOptionsBox(LanguageResourceHandler.LANGUAGE_KEY, languages, (s) -> changeLanguage(s));
    languageBox.setId((new CSSidHandler()).getStringFromKey(CSSidHandler.LANGUAGE_COMBOBOX_KEY));
    return languageBox;

  }


  private Node makeStyleSelector(){
    //make GUI component allowing user to choose between dark mode, Duke colors, basic style, etc..
    Node styleBox =  makeOptionsBox(LanguageResourceHandler.STYLE_SELECTOR_KEY, styleNameToPath.keySet(), (s) -> changeStyle(styleNameToPath.get(s)));
    styleBox.setId((new CSSidHandler()).getStringFromKey(CSSidHandler.STYLE_COMBOBOX_KEY));
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
//    fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("SIM Files", "*.sim"));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    handleSelectedFile(selectedFile);
  }

  /**
   * Handle a file that has been inputted. This is only public so that we can use it for testing.
   * I cannot figure out how to do TestFX to input a file, so I had to call this method in the tests - Keith
   * @param selectedFile
   */
  public void handleSelectedFile(File selectedFile){
    //take the file inputted by a user, and send it to the controller for parsing. Show error messages if neccessary
    SimulationDisplay oldDisplay = mySimulationDisplay;
    mySimulationDisplay = new SimulationDisplay(myLanguageResourceHandler);
    try{

      addSimulationDisplay(mySimulationDisplay.makeDisplay(selectedFile), oldDisplay);
    } catch (Exception e){
      mySimulationDisplay = oldDisplay;
      displayErrorMessage(e.getMessage());;


    }
  }



  private void addSimulationDisplay(Node newDisplay, SimulationDisplay oldDisplay) {
    //add display for a new simulation. First remove the old display, if there was one

    if (oldDisplay != null) {
      if (oldDisplay.getMyNode() != null) {
        Platform.runLater(new Runnable() {
          @Override public void run() {
            mainPane.getChildren().remove(oldDisplay.getMyNode());
          }
        });
      }
    }
    mySubDisplays.clear();
    mySubDisplays.add(mySimulationDisplay);
    Platform.runLater(new Runnable() {
      @Override public void run() {
        mainPane.getChildren().add(newDisplay);
      }
    });
  }


  /**
   * get the simulation display. Used only for testing
   * @return the simulation display
   */
  public SimulationDisplay getSimDisplay(){
    return mySimulationDisplay;
  }





}
