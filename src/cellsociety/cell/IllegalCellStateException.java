package cellsociety.cell;

public class IllegalCellStateException extends Exception{
    public IllegalCellStateException(String errorMessage) {
        super(errorMessage);
    }

    public IllegalCellStateException() {

    }
}
