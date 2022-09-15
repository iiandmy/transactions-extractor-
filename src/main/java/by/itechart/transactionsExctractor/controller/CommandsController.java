package by.itechart.transactionsExctractor.controller;

import by.itechart.transactionsExctractor.commands.factory.CommandFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommandsController {

    private final CommandFactory commandFactory;

    @GetMapping("/commands")
    public ResponseEntity<List<String>> getAvailableCommands() {
        return ResponseEntity.ok(commandFactory.getAvailableCommands());
    }
}
