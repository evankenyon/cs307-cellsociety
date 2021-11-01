package cellsociety.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

public class SimGenerator {
  Properties simInfo;

  public SimGenerator(Properties simInfo) {
    this.simInfo = simInfo;
  }

  /**
   * Save a .sim file for the simulation, with properties specified in propertyToValue
   * @param propertyToValue maps keys like "Author" and "Title" to their corresponding values for the simulation
   */
  public void createSimFile(String fileName, Map<String, String> propertyToValue) throws IOException{
    String filePath = String.format("./data/saved/%s/program-" + "%s.sim", simInfo.getProperty("Type"), fileName);
    simInfo.setProperty("InitialStates", String.format("/saved/%s/program-" + "%s.csv", simInfo.getProperty("Type"), fileName));

    try {
      for (String property : propertyToValue.keySet()){
        simInfo.setProperty(property, propertyToValue.get(property));
      }
    } catch (NullPointerException e) {
      // Don't do anything since shouldn't change simInfo if propertyToValue map is null
    }

    // Borrowed code to output Properties to a file from
    // https://www.java2novice.com/java-file-io-operations/create-store-property-file/
    OutputStream os = new FileOutputStream(filePath);
    simInfo.store(os, null);
  }
}
