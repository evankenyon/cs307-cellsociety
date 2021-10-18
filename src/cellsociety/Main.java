package cellsociety;


import javafx.application.Application;
import javafx.stage.Stage;
import cellsociety.view.MainView;
import cellsociety.view.SimulationView;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application{
    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }


    /**
     * Organize display of game in a scene and start the game.
     */
    @Override
    public void start(Stage stage) {
        SimulationView sv = new SimulationView();
        stage.setScene(sv.makeSimulationScene());
        sv.setStage(stage);
        stage.show();
    }
}
