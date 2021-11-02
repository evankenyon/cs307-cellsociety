package cellsociety.resourceHandlers;

import java.util.Properties;
import java.io.FileInputStream;

/**
 * Objects of this class are used to get the css id associated with a given key
 *
 * @author Keith Cressman
 */
public class CSSidHandler {

  public static final String IDS_PATH = "src/cellsociety/ResourceHandlers/CSSids.properties";

  public static final String HISTOGRAM_BACKGROUND_KEY = "HistogramBackground";
  public static final String INFO_DISPLAY_BACKGROUND_KEY = "InfoBackground";
  public static final String FILENAME_SAVE_KEY = "FileNameSave";
  public static final String LANGUAGE_COMBOBOX_KEY = "LanguageBox";
  public static final String STYLE_COMBOBOX_KEY = "StyleBox";

  private Properties idProperties;

  public CSSidHandler() {
    idProperties = new Properties();
    try {
      idProperties.load(new FileInputStream(IDS_PATH));
    } catch (Exception e) {
    }
  }

  /**
   * get the key/label associated with this key
   *
   * @param key should be one of the static final String constants defiened in this class
   * @return the label associated with the key
   */
  public String getStringFromKey(String key) {
    return idProperties.getProperty(key);
  }

}
