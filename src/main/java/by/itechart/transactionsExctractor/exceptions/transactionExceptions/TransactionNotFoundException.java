package by.itechart.transactionsExctractor.exceptions.transactionExceptions;

public class TransactionNotFoundException extends TransactionException {

    public TransactionNotFoundException(String id) {
        super("No transactions with id " + id);
    }

    public TransactionNotFoundException(Exception e) {
        super(e);
    }

}
