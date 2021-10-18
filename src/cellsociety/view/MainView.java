package cellsociety.view;

import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Group;

import java.util.List;
import java.util.ArrayList;


public class MainView {
  private Stage mainStage;

  public static final int HEIGHT = 600;
  public static final int WIDTH = 800;

  public MainView(Stage stage){
    mainStage = stage;
    SimulationView simView = new SimulationView();
    stage.setScene(simView.makeSimulationScene());


  }





}
