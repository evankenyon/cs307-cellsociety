package cellsociety.Utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class SimParser {
  private static final String DEFAULT_RESOURCE_PACKAGE =
      SimParser.class.getPackageName() + ".resources.";
  private static final String REQUIRED_KEYS_FILENAME = "RequiredKeys";
  private static final String NUM_REQUIRED_KEYS_FILENAME = "NumRequiredKeys";
  private static final String VALID_VALUES = "ValidValues";
  private static final String KEY_STANDARDIZATION_FILENAME = "KeyStandardization";

  private Properties simulationConfig;
  private ResourceBundle requiredKeys;


  public SimParser() {
    simulationConfig = new Properties();
    requiredKeys = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + REQUIRED_KEYS_FILENAME);
  }

  public void setupKeyValuePairs(File simFile) throws InputMismatchException, NullPointerException {
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
    try {
      simulationConfig.load(simFileInputStream);
    } catch (IOException e) {
      throw new NullPointerException();
    }
    standardizeKeys();
    handleIllegalInput();
  }

  private void standardizeKeys() {
    for (String key : simulationConfig.stringPropertyNames()) {
      ResourceBundle keyStandardization = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + KEY_STANDARDIZATION_FILENAME);
      String value = simulationConfig.getProperty(key);
      simulationConfig.remove(key);
      try {
        simulationConfig.setProperty(keyStandardization.getString(key), value);
      } catch (MissingResourceException e) {
        throw new InputMismatchException();
      }
    }
  }

  private void handleIllegalInput() {
    int numRequiredKeys = 0;
    for(String key: requiredKeys.keySet()) {
      if (!simulationConfigContainsRequiredKey(key)) {
        throw new InputMismatchException();
      }
      numRequiredKeys++;
    }
    if(numRequiredKeys != Integer.parseInt(ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + NUM_REQUIRED_KEYS_FILENAME).getString("NumRequiredKeys"))) {
      throw new InputMismatchException();
    }

    ResourceBundle validSimulationNames = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + VALID_VALUES);
    if(!List.of(validSimulationNames.getString("ValidSimulationNames").split(",")).contains(simulationConfig.getProperty("Type"))) {
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
