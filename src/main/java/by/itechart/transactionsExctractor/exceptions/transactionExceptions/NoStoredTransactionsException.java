package by.itechart.transactionsExctractor.exceptions.transactionExceptions;

public class NoStoredTransactionsException extends TransactionException {

    public NoStoredTransactionsException() {
        super("No transactions currently stored");
    }

    public NoStoredTransactionsException(Exception e) {
        super(e);
    }

}
