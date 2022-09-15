package by.itechart.transactionsExctractor.commands.commandInterface;

import by.itechart.transactionsExctractor.entity.Transaction;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public abstract class Command {

    public abstract Transaction execute();

}
