package cellsociety.Rule;

import java.lang.reflect.InvocationTargetException;

public interface RulesInterface {

  public void setState()
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
}
