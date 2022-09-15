package by.itechart.transactionsExctractor.exceptions.parserExceptions;

public class IllegalParsedValueException extends ParserException {
    //Can't generalize the message due to this exception's usage
    public IllegalParsedValueException(String message) {
        super(message);
    }

    public IllegalParsedValueException(RuntimeException e) {
        super(e);
    }

}
