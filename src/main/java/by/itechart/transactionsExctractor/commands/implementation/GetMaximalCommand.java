package by.itechart.transactionsExctractor.commands.implementation;

import by.itechart.transactionsExctractor.commands.commandInterface.Command;
import by.itechart.transactionsExctractor.entity.Transaction;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.NoStoredTransactionsException;
import by.itechart.transactionsExctractor.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Component("GET_MAXIMAL")
public class GetMaximalCommand extends Command {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction execute() {
        return transactionRepository.findFirstByOrderByAmountDesc().orElseThrow(NoStoredTransactionsException::new);
    }

    @Override
    public String toString() {
        return "Get maximal transaction";
    }

}