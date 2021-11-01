package cellsociety.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cellsociety.view.FileSavePopup;

public class SimGenerator {
  Properties simInfo;

  public SimGenerator(Properties simInfo) {
    this.simInfo = simInfo;
  }

  public void updateReplaceableSimInfo(Map<String, String> newSimInfo) {
    for(String key : newSimInfo.keySet()) {
      simInfo.replace(key, newSimInfo.get(key));
    }
  }

  public void createSimFile(String fileName) {
    OutputStream os = null;
    String filePath = String.format("./data/game_of_life/saved/program-" + "%s.sim", fileName);
    simInfo.setProperty("InitialStates", String.format("game_of_life/saved/program-" + "%s.csv", fileName));

    // Borrowed code to output Properties to a file from
    // https://www.java2novice.com/java-file-io-operations/create-store-property-file/
    try {
      os = new FileOutputStream(filePath);
      simInfo.store(os, null);
    } catch (FileNotFoundException e) {
      // TODO: handle exceptions
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Save a .sim file for the simulation, with properties specified in propertyToValue
   * @param propertyToValue maps keys like "Author" and "Title" to their corresponding values for the simulation
   */
  public void createSimFile(Map<String, String> propertyToValue) throws IOException{
    OutputStream os = null;

    for (String property : propertyToValue.keySet()){
      simInfo.setProperty(property, propertyToValue.get(property));
    }
    //simInfo.setProperty("InitialStates", String.format("game_of_life/saved/program-" + "%s.csv", fileName));

    // Borrowed code to output Properties to a file from
    // https://www.java2novice.com/java-file-io-operations/create-store-property-file/
    os = new FileOutputStream(propertyToValue.get(FileSavePopup.INITIAL_STATES));
    simInfo.store(os, null);
  }
}
