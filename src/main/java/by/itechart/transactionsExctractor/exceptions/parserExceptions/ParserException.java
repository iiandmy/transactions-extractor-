package by.itechart.transactionsExctractor.exceptions.parserExceptions;

public class ParserException extends RuntimeException {

    public ParserException(Exception e) {
        super(e);
    }

    public ParserException(String message) {
        super(message);
    }

}
