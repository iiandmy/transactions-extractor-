package by.itechart.transactionsExctractor.statistics;

import by.itechart.transactionsExctractor.entity.Transaction;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.NoStoredTransactionsException;
import by.itechart.transactionsExctractor.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class Statistics {

    private final TransactionRepository transactionRepository;

    private long transactionCount = 0;
    private long successfulCount = 0;
    private long failedCount = 0;
    private Transaction minTransaction = new Transaction();
    private Transaction maxTransaction = new Transaction();

    public long getTransactionCount() {
        this.transactionCount = transactionRepository.findAll().size();
        return transactionCount;
    }

    public long getFailedCount() {
        this.failedCount = transactionRepository.countByStatus(false);
        return failedCount;
    }

    public long getSuccessfulCount() {
        this.successfulCount = transactionRepository.countByStatus(true);
        return successfulCount;
    }

    public Transaction getMaxTransaction() {
        this.maxTransaction = transactionRepository.findFirstByOrderByAmountDesc().orElseThrow(NoStoredTransactionsException::new);
        return maxTransaction;
    }

    public Transaction getMinTransaction() {
        this.minTransaction = transactionRepository.findFirstByOrderByAmountAsc().orElseThrow(NoStoredTransactionsException::new);
        return minTransaction;
    }

    @Override
    public String toString() {
        return "Total number of transactions: " +
                getTransactionCount() +
                "\nWhere successful: " +
                getSuccessfulCount() +
                "\nFailed: " +
                getFailedCount() +
                "\nMin transaction: " +
                simplifiedStringOf(getMinTransaction()) +
                "\nMax transaction: " +
                simplifiedStringOf(getMaxTransaction());
    }

    private String simplifiedStringOf(Transaction transaction) {
        return transaction.getAmount() + " " + transaction.getCurrency();
    }

}
