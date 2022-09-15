package by.itechart.transactionsExctractor.repository;

import by.itechart.transactionsExctractor.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, String> {
}
