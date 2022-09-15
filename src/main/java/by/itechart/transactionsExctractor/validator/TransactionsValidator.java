package by.itechart.transactionsExctractor.validator;

import by.itechart.transactionsExctractor.entity.Transaction;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.IllegalIdException;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.TransactionNotValidException;

public class TransactionsValidator {

    //TODO UUID
    public static void validateId(String id) { //Only needed internally here for now, yet public for future problems
        if (id == null || !id.matches("[a-z0-9-]{36}")) {
            throw new IllegalIdException(id);
        }
    }

    public static void validateTransactionEntity(Transaction transaction) {
        if (
            transaction == null ||
            transaction.getCurrency() == null ||
            !transaction.getCurrency().matches("[a-zA-Z]+") ||
            transaction.getCreationTime() == null ||
            transaction.getCreationTime().isEmpty()
        ) {
            throw new TransactionNotValidException(transaction);
        }
        validateId(transaction.getTransactionId());
        validateId(transaction.getUser().getId());
    }

    //TODO separate validations for parser

}
