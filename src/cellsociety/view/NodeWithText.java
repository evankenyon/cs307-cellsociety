package cellsociety.view;

/**
 * Classes which implement this interface will have elements that can change their text.
 * This interface was made to assist with changing the text when the language is changed.
 * @author Keith Cressman
 */
public interface NodeWithText {

  /**
   * change the text on this element
   * @param newText will be the new text
   */
  public void setText(String newText);

  /**
   * get the text for this element
   * @return the text on this element
   */
  public String getText();

}
