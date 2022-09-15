package by.itechart.transactionsExctractor.exceptions.parserExceptions;

public class NoSuchFileException extends ParserException {

    public NoSuchFileException(String fileName) {
        super("File \"" + fileName + "\" does not exist");
    }

    public NoSuchFileException(Exception e) {
        super(e);
    }

}
