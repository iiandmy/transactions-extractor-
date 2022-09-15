package by.itechart.transactionsExctractor.service;

import by.itechart.transactionsExctractor.entity.Operator;
import by.itechart.transactionsExctractor.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperatorsService {

    private final OperatorRepository operatorsRepository;

    public void saveOperator(String login, String password) {
        operatorsRepository.save(new Operator(login, password));
    }

    public void saveOperator(Operator operator) { operatorsRepository.save(operator); }
}
