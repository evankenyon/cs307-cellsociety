package cellsociety;


import cellsociety.Model.Model;
import cellsociety.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import cellsociety.view.MainView;


/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  /**
   * A method to test (and a joke :).
   */
  public double getVersion() {
    return 0.001;
  }


  /**
   * Organize display of game in a scene and start the game.
   */
  @Override
  public void start(Stage stage) {
    MainView sv = new MainView();
    Controller myController = new Controller();
    Model myModel = new Model();

    //Set the relationships between the view/controller/and model
    // sv.setMyModel(myModel);
    //myController.setModel(myModel);
    //myController.setMainView(sv);
    //sv.setMyController(myController);

    stage.setScene(sv.makeSimulationScene());
    sv.setStage(stage);
    stage.show();
  }
}
