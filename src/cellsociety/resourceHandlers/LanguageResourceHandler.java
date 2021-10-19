package cellsociety.resourceHandlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.io.FileInputStream;
import java.util.Locale;

import java.util.Map;
import java.util.HashMap;

public class LanguageResourceHandler {
  public static final String LANGUAGE_BUNDLE_NAME = "Langauge";
  public static final String SETTINGS_KEY = "Settings";
  public static final String ABOUT_KEY = "About";
  public static final String LANGUAGE_KEY = "Language";
  public static final String SELECT_FILE_KEY = "SelectFile";
  public static final String START_SIMULATIONS_KEY = "StartSimulations";
  public static final String CHANGE_ENGLISH_KEY = "ChangeEnglish";
  public static final String CHANGE_SPANISH_KEY = "ChangeSpanish";
  public static final String FILE_SELECTOR_TITLE_KEY = "FileSelectTitle";
  public static final String ENGLISH = "English";
  public static final String SPANISH = "Spanish";
  public static final String[] SUPPORTED_LANGUAGES = {ENGLISH, SPANISH};
  private final Locale englishLocale = new Locale(ENGLISH, "EN");
  private final Locale spanishLocale = new Locale(SPANISH, "ES");
  private Map<String, Locale> langToLocale;


  private ResourceBundle myResourceBundle;

  public LanguageResourceHandler(){
    setUpLangToLocaleMap();
    myResourceBundle = ResourceBundle.getBundle(LANGUAGE_BUNDLE_NAME, langToLocale.get(ENGLISH));
  }

  private void setUpLangToLocaleMap(){
    langToLocale = new HashMap<>();
    langToLocale.put(ENGLISH, englishLocale);
    langToLocale.put(SPANISH, spanishLocale);
  }



  /**
   * Get the String used to display Settings
   * @return a String like "Settings" which is meant for the GUI
   */
  public String getSettingsString(){
    return myResourceBundle.getString(SETTINGS_KEY);
  }


  /**
   * Get the String used on the About button on the GUI
   * @return a String like "About" which is meant for the GUI
   */
  public String getAboutString(){
    return myResourceBundle.getString(ABOUT_KEY);
  }

  /**
   * Get the String used on the Language label on the GUI
   * @return a String like "Language" which is meant for the GUI
   */
  public String getLanguageString(){
    return myResourceBundle.getString(LANGUAGE_KEY);
  }

  /**
   * Get the String used on the Select File button
   * @return a String like "Select File" which is meant for the GUI
   */
  public String getSelectFileString(){
    return myResourceBundle.getString(SELECT_FILE_KEY);
  }

  /**
   * Get the String used on the Start Simulations Button
   * @return a String like "Start Simulation(s)" which is meant for the GUI
   */
  public String getStartSimulationsString(){
    return myResourceBundle.getString(START_SIMULATIONS_KEY);
  }

  /**
   * Get the String used on the Change to English Button
   * @return a String like "English" which is meant for the GUI
   */
  public String getChangeEnglishString(){
    return myResourceBundle.getString(CHANGE_ENGLISH_KEY);
  }

  /**
   * Get the String used on the Change to Spanish Button
   * @return a String like "Spanish" which is meant for the GUI
   */
  public String getChangeSpanishString(){
    return myResourceBundle.getString(CHANGE_SPANISH_KEY);
  }

  /**
   * Get the String used on the title of the file selector
   * @return a String like "File selector" which is meant for the GUI
   */
  public String getSelectFileTitleString(){
    return myResourceBundle.getString(SELECT_FILE_KEY);
  }



  /**
   * use the Spanish labels
   */
  public void changeToSpanish(){
    myResourceBundle = ResourceBundle.getBundle(LANGUAGE_BUNDLE_NAME, langToLocale.get(SPANISH));
  }

  /**
   * use the English labels
   */
  public void changeToEnglish(){
    myResourceBundle = ResourceBundle.getBundle(LANGUAGE_BUNDLE_NAME, langToLocale.get(ENGLISH));
  }

  /**
   * get the key/label associated with this key
   * @param key should be one of the static final String constants defiened in this class
   * @return the label associated with the key
   */
  public String getStringFromKey(String key){
    return myResourceBundle.getString(key);
  }








}
