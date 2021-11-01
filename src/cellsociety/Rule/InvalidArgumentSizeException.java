package cellsociety.Rule;

public class InvalidArgumentSizeException extends Exception{
    //FIXME: Implement properties file with names of these messages
    public static final String HEADLINE = "Error";
    public InvalidArgumentSizeException(String errorMessage) {
        super(errorMessage);
    }
}
