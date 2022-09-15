package by.itechart.transactionsExctractor.exceptions.transactionExceptions;

public class IllegalIdException extends TransactionException {

    public IllegalIdException(String id) {
        super("Id " + id + " is illegal!");
    }

    public IllegalIdException(Exception e) {
        super(e);
    }

}
