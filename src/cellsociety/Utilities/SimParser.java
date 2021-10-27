package cellsociety.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class SimParser {

  private Properties simulationConfig;

  public SimParser() {
    simulationConfig = new Properties();
  }

  // TODO: handle exception properly
  public void setupKeyValuePairs(File csvFile) throws IOException {
    // Used https://mkyong.com/java/java-read-a-file-from-resources-folder/ to learn how to properly
    // setup pathname
    String pathName =
        getRelativeParentDirectory( csvFile.getParent()) + csvFile.getName().replace(".csv", ".sim");
    // Borrowed code for making InputStream from
    // https://www.baeldung.com/convert-file-to-input-stream
    InputStream simFileInputStream = this.getClass().getClassLoader().getResourceAsStream(pathName);
    simulationConfig.load(simFileInputStream);
  }

  public Properties getSimulationConfig() {
    return simulationConfig;
  }

  private String getRelativeParentDirectory(String absoluteDirectory) {
    String[] allDirectories = absoluteDirectory.split("/");
    String relativeDirectory = "";
    boolean shouldAdd = false;
    for (String directory : allDirectories) {
      if (shouldAdd) {
        relativeDirectory += directory + "/";
      }
      if (directory.equals("data")) {
        shouldAdd = true;
      }
    }
    return relativeDirectory;
  }
}
