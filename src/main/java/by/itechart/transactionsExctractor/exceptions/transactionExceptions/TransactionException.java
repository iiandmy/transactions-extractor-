package by.itechart.transactionsExctractor.exceptions.transactionExceptions;

public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Exception e) {
        super(e);
    }

}
