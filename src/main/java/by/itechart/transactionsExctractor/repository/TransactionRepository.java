package by.itechart.transactionsExctractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.itechart.transactionsExctractor.entity.Transaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findTop5ByOrderByAmountDesc();

    Optional<Transaction> findFirstByOrderByAmountDesc();

    Optional<Transaction> findFirstByOrderByAmountAsc();

    long countByStatus(boolean status);

}

