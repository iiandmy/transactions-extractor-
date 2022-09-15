package by.itechart.transactionsExctractor.controller;

import by.itechart.transactionsExctractor.entity.Operator;
import by.itechart.transactionsExctractor.service.OperatorsService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorsService operatorService;

    @PostMapping("/createOperator")
    public ResponseEntity<Operator> createOperator(@RequestParam("login") String login, @RequestParam("password") String password) {
        // TODO: Check for possible exceptions
        Operator operatorToSave = new Operator(login, password);
        operatorService.saveOperator(operatorToSave);
        return ResponseEntity.ok(operatorToSave);
    }
}
