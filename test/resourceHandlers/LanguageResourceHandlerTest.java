package resourceHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import cellsociety.resourceHandlers.LanguageResourceHandler;

public class LanguageResourceHandlerTest {
  LanguageResourceHandler myHandler;

  @BeforeEach
  void setup(){
    myHandler = new LanguageResourceHandler();
  }

  @Test
  void testGetSettingsEnglish(){
    String expected = "Settings";
    String actual = myHandler.getSettingsString();
    assertEquals(expected, actual);
  }

  @Test
  void testGetAboutEnglish(){
    String expected = "About";
    String actual = myHandler.getAboutString();
    assertEquals(expected, actual);
  }

  @Test
  void testGetLanguageEnglish(){
    String expected = "Choose Language";
    String actual = myHandler.getLanguageString();
    assertEquals(expected, actual);
  }

  @Test
  void testGetStartSimulationsEnglish(){
    String expected = "Start Simulation(s)";
    String actual = myHandler.getStartSimulationsString();
    assertEquals(expected, actual);
  }

  @Test
  void testGetSelectFileEnglish(){
    String expected = "Select a File";
    String actual = myHandler.getSelectFileString();
    assertEquals(expected, actual);
  }

  @Test
  void testChangeToSpanishSimple(){
    String expected = "About";
    String actual = myHandler.getAboutString();
    assertEquals(expected, actual);
    myHandler.changeToSpanish();
    expected = "AboutS";
    actual = myHandler.getAboutString();
    assertEquals(expected, actual);
  }

  @Test
  void testChangeToSpanishComplex(){
    String expected = "About";
    String actual = myHandler.getAboutString();
    assertEquals(expected, actual);
    myHandler.changeToSpanish();
    myHandler.changeToSpanish();
    myHandler.changeToEnglish();
    myHandler.changeToEnglish();
    myHandler.changeToSpanish();
    expected = "AboutS";
    actual = myHandler.getAboutString();
    assertEquals(expected, actual);
  }

  @Test
  void testChangeToEnglishSimple(){
    myHandler.changeToSpanish();
    myHandler.changeToEnglish();
    testGetLanguageEnglish();
  }

  @Test
  void testChangeToEnglishComplex(){
    myHandler.changeToSpanish();
    myHandler.changeToEnglish();
    myHandler.changeToEnglish();
    myHandler.changeToSpanish();
    myHandler.changeToEnglish();
    myHandler.changeToEnglish();
    myHandler.changeToEnglish();
    testGetLanguageEnglish();
  }



}
