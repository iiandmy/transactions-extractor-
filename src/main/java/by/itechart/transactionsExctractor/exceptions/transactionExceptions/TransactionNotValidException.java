package by.itechart.transactionsExctractor.exceptions.transactionExceptions;

import by.itechart.transactionsExctractor.entity.Transaction;

public class TransactionNotValidException extends TransactionException {

    public TransactionNotValidException(Transaction transaction) {
        super("Transaction " + transaction + " is illegal!");
    }

    public TransactionNotValidException(Exception e) {
        super(e);
    }

}
