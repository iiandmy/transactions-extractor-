package by.itechart.transactionsExctractor.commands.factory;

import by.itechart.transactionsExctractor.commands.commandInterface.Command;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.UnknownCommandException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandFactory {

    private final Map<String, Command> commandMap;

    public Command getCommand(String command_id) {
        if(!commandMap.containsKey(command_id)) {
            throw new UnknownCommandException(command_id);
        }
        return commandMap.get(command_id);
    }

    public List<String> getAvailableCommands() {
        return new ArrayList<>(){{
            commandMap.forEach((key, val) -> {
                add(key);
            });
        }};
    }
}
