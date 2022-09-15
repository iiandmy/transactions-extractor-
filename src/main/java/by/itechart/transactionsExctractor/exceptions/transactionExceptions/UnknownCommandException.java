package by.itechart.transactionsExctractor.exceptions.transactionExceptions;

public class UnknownCommandException extends TransactionException {

    public UnknownCommandException(String command_id) {
        super("No command with id " + command_id);
    }

    public UnknownCommandException(Exception e) {
        super(e);
    }

}
