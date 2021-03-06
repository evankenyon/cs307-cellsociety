package cellsociety.resourceHandlers;

import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * objects of this class help handle properties relating to languages/text to do: make separate
 * pause and resume button
 */
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
  public static final String BAD_FILE_KEY = "BadFile";
  public static final String PAUSE_KEY = "Pause";
  public static final String RESUME_KEY = "Resume";
  public static final String SAVE_FILE_KEY = "SaveFile";
  public static final String NEW_SIMULATION_KEY = "NewSimulation";
  public static final String ONE_STEP_KEY = "OneStep";
  public static final String DARK_MODE_KEY = "DarkMode";
  public static final String DUKE_MODE_KEY = "DukeMode";
  public static final String BASIC_MODE_KEY = "BasicMode";
  public static final String STYLE_SELECTOR_KEY = "SelectStyle";
  public static final String SPEED_SLIDER_KEY = "SpeedSlider";
  public static final String SIM_TYPE_SAVE_KEY = "SimulationTypeSave";
  public static final String SIM_TITLE_SAVE_KEY = "TitleSave";
  public static final String SIM_AUTHOR_SAVE_KEY = "AuthorSave";
  public static final String SIM_DESCRIPTION_SAVE_KEY = "DescriptionSave";
  public static final String SIM_FILENAME_SAVE_KEY = "FileNameSave";
  public static final String SIM_OTHER_SAVE_KEY = "OtherSave";
  public static final String SAVE_BUTTON_POPUP_KEY = "FinalSave";
  public static final String FAILED_SAVE_KEY = "FailedToSave";
  public static final String HISTOGRAM_TITLE_KEY = "HistogramTitle";
  public final static String INFO_DISPLAY_TITLE_KEY = "InfoTitle";
  public final static String INFO_STATE_KEY = "InfoState";
  public final static String INFO_NAME_KEY = "InfoName";
  public final static String INFO_DISPLAY_KEY = "InfoDisplay";
  public final static String INFO_CHANGE_KEY = "InfoChange";
  public final static String SHAPES_KEY = "Shapes";
  public final static String SHOW_GRID_KEY = "ShowCellGrid";
  public final static String HIDE_GRID_KEY = "HideCellGrid";
  public final static String SHOW_HISTOGRAM_KEY = "ShowHistogram";
  public final static String HIDE_HISTOGRAM_KEY = "HideHistogram";
  public final static String SHOW_INFO_KEY = "ShowInfo";
  public final static String HIDE_INFO_KEY = "HideInfo";
  public final static String EDGE_POLICY_KEY = "EdgePolicy";
  public final static String NEIGHBOR_ARRANGEMENT_KEY = "NeighborArrangement";
  public final static String FAILED_REFLECT_KEY = "ReflectFail";
  public final static String ILLEGAL_STATE_KEY = "IllegalState";
  public final static String ILLEGAL_STATE_USER_KEY = "IllegalStateUser";
  public final static String WRONG_FILE_TYPE_KEY = "WrongFileType";
  public final static String OUT_OF_BOUNDS_KEY = "OutOfBounds";
  public final static String ILLEGAL_PARAMS_KEY = "IllegalParams";
  public final static String INVALID_DIMENSIONS_KEY = "InvalidDimensions";
  public final static String BIG_ROW_KEY = "BigRow";

  public static final String ENGLISH = "English";
  public static final String SPANISH = "Spanish";
  public static final String GERMAN = "German";

  private final Locale englishLocale = new Locale(ENGLISH, "EN");
  private final Locale spanishLocale = new Locale(SPANISH, "ES");
  private final Locale germanLocale = new Locale(GERMAN, "DE");
  private Map<String, Locale> langToLocale;


  private ResourceBundle myResourceBundle;

  public LanguageResourceHandler() {
    setUpLangToLocaleMap();
    myResourceBundle = ResourceBundle.getBundle(LANGUAGE_BUNDLE_NAME, langToLocale.get(ENGLISH));
  }

  private void setUpLangToLocaleMap() {
    langToLocale = new HashMap<>();
    langToLocale.put(ENGLISH, englishLocale);
    langToLocale.put(SPANISH, spanishLocale);
    langToLocale.put(GERMAN, germanLocale);
  }


  /**
   * Get the String used to display Settings
   *
   * @return a String like "Settings" which is meant for the GUI
   */
  public String getSettingsString() {
    return myResourceBundle.getString(SETTINGS_KEY);
  }


  /**
   * Get the String used on the About button on the GUI
   *
   * @return a String like "About" which is meant for the GUI
   */
  public String getAboutString() {
    return myResourceBundle.getString(ABOUT_KEY);
  }

  /**
   * Get the String used on the Language label on the GUI
   *
   * @return a String like "Language" which is meant for the GUI
   */
  public String getLanguageString() {
    return myResourceBundle.getString(LANGUAGE_KEY);
  }

  /**
   * Get the String used on the Select File button
   *
   * @return a String like "Select File" which is meant for the GUI
   */
  public String getSelectFileString() {
    return myResourceBundle.getString(SELECT_FILE_KEY);
  }

  /**
   * Get the String used on the Start Simulations Button
   *
   * @return a String like "Start Simulation(s)" which is meant for the GUI
   */
  public String getStartSimulationsString() {
    return myResourceBundle.getString(START_SIMULATIONS_KEY);
  }

  /**
   * Get the String used on the Change to English Button
   *
   * @return a String like "English" which is meant for the GUI
   */
  public String getChangeEnglishString() {
    return myResourceBundle.getString(CHANGE_ENGLISH_KEY);
  }

  /**
   * Get the String used on the Change to Spanish Button
   *
   * @return a String like "Spanish" which is meant for the GUI
   */
  public String getChangeSpanishString() {
    return myResourceBundle.getString(CHANGE_SPANISH_KEY);
  }

  /**
   * Get the String used on the title of the file selector
   *
   * @return a String like "File selector" which is meant for the GUI
   */
  public String getSelectFileTitleString() {
    return myResourceBundle.getString(SELECT_FILE_KEY);
  }

  /**
   * change the  language being used
   *
   * @param newLanguage should be a string like "English" or "Spanish"
   */
  public void changeLanguage(String newLanguage) {
    try {
      myResourceBundle = ResourceBundle.getBundle(LANGUAGE_BUNDLE_NAME,
          langToLocale.get(newLanguage));
    } catch (Exception e) {
      //do nothing
    }
  }


  /**
   * get the key/label associated with this key
   *
   * @param key should be one of the static final String constants defiened in this class
   * @return the label associated with the key
   */
  public String getStringFromKey(String key) {
    return myResourceBundle.getString(key);
  }

  /**
   * get the strings representing all the possible languages
   *
   * @return a collection like {"English", "Spanish", "German"}
   */
  public Collection<String> getSupportedLanguages() {
    return langToLocale.keySet();
  }


}
