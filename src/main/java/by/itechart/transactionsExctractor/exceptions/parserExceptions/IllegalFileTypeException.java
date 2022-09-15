package by.itechart.transactionsExctractor.exceptions.parserExceptions;

public class IllegalFileTypeException extends ParserException {

    public IllegalFileTypeException(String fileType) {
        super("File type \"" + fileType + "\" not supported");
    }

    public IllegalFileTypeException(Exception e) {
        super(e);
    }

}
