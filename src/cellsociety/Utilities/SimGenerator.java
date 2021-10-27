package cellsociety.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
}
