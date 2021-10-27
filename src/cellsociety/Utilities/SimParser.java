package cellsociety.Utilities;

import cellsociety.Model.Model;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class SimParser {
  private static final String DEFAULT_RESOURCE_PACKAGE =
      SimParser.class.getPackageName() + ".resources.";
  private static final String OPTIONAL_KEYS_FILENAME = "OptionalKeys";
  private static final String REQUIRED_KEYS_FILENAME = "RequiredKeys";
  private static final String NUM_REQUIRED_KEYS_FILENAME = "NumRequiredKeys";

  private Properties simulationConfig;
  private ResourceBundle requiredKeys;
  private ResourceBundle optionalKeys;

  public SimParser() {
    simulationConfig = new Properties();
    requiredKeys = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + REQUIRED_KEYS_FILENAME);
    optionalKeys = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + OPTIONAL_KEYS_FILENAME);
  }

  // TODO: handle exception properly
  public void setupKeyValuePairs(File simFile) throws IOException, InputMismatchException {
    // Used https://mkyong.com/java/java-read-a-file-from-resources-folder/ to learn how to properly
    // setup pathname
    File currFile = simFile;
    StringBuilder pathName = new StringBuilder(simFile.getName());
    while (!currFile.getParentFile().getName().equals("data")) {
      pathName.insert(0, String.format("%s/", currFile.getParentFile().getName()));
      currFile = currFile.getParentFile();
    }
    // Borrowed code for making InputStream from
    // https://www.baeldung.com/convert-file-to-input-stream
    InputStream simFileInputStream = this.getClass().getClassLoader().getResourceAsStream(pathName.toString());
    simulationConfig.load(simFileInputStream);
    handleIllegalInput();
  }

  private void handleIllegalInput() {
    int numRequiredKeys = 0;
    for(String key: requiredKeys.keySet()) {
      if (!simulationConfigContainsRequiredKey(key)) {
        // TODO: actually handle
        throw new InputMismatchException();
      }
      numRequiredKeys++;
    }
    if(numRequiredKeys != Integer.parseInt(ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + NUM_REQUIRED_KEYS_FILENAME).getString("NumRequiredKeys"))) {
      // TODO: actually handle
      throw new InputMismatchException();
    }
  }

  private boolean simulationConfigContainsRequiredKey(String key) {
    for (String possibleKey : requiredKeys.getString(key).split(",")) {
      if(simulationConfig.containsKey(possibleKey)) {
        return true;
      }
    }
    return false;
  }

  public Properties getSimulationConfig() {
    return simulationConfig;
  }
}
