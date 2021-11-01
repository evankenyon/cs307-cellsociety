package cellsociety.Utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimGeneratorTest {
  private SimGenerator simGenerator;

  @BeforeEach
  void setUp() throws IOException {
    Properties props = new Properties();
    FileInputStream defaultStream = new FileInputStream("./data/game_of_life/blinkers.sim");
    props.load(defaultStream);
    defaultStream.close();
    simGenerator = new SimGenerator(props);
  }

  @Test
  void createSimFile() throws IOException {
    simGenerator.createSimFile("junit", null);
    Properties expectedProps = new Properties();
    FileInputStream stream = new FileInputStream("./data/game_of_life/blinkers.sim");
    expectedProps.load(stream);
    stream.close();
    Properties actualProps = new Properties();
    stream = new FileInputStream("./data/game_of_life/saved/program-junit.sim");
    actualProps.load(stream);
    stream.close();
    for(String key : expectedProps.stringPropertyNames()) {
      Assertions.assertTrue(actualProps.stringPropertyNames().contains(key));
    }
  }
}