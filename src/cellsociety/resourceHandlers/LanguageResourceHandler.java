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
  public static final String ENGLISH = "English";
  public static final String SPANISH = "Spanish";
  private final String[] supportedLanguages = {ENGLISH, SPANISH};
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








}
