package by.itechart.transactionsExctractor.service;

import by.itechart.transactionsExctractor.commands.factory.CommandFactory;
import by.itechart.transactionsExctractor.entity.Transaction;

import by.itechart.transactionsExctractor.exceptions.parserExceptions.ParserException;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.*;
import by.itechart.transactionsExctractor.repository.TransactionRepository;
import by.itechart.transactionsExctractor.statistics.Statistics;
import by.itechart.transactionsExctractor.validator.TransactionsValidator;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import by.itechart.transactionsExctractor.exceptions.parserExceptions.NoSuchFileException;
import by.itechart.transactionsExctractor.exceptions.parserExceptions.IllegalFileTypeException;
import by.itechart.transactionsExctractor.parser.factory.ParserFactory;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionsService {

    private final TransactionRepository transactionRepository;

    private final ParserFactory parserFactory;

    private final CommandFactory commandFactory;

    private final Statistics statistics;

    public List<Transaction> getTopFiveTransactions() {
        return transactionRepository.findTop5ByOrderByAmountDesc();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getMinOrMax(@NotNull String command_id) {
        return commandFactory.getCommand(command_id).execute();
    }

    public String getStatistic() {
        return statistics.toString();
    }

    //TODO validate and throw
    public boolean saveTransaction(@NotNull Transaction transaction) {
        TransactionsValidator.validateTransactionEntity(transaction);
        transactionRepository.save(transaction);
        log.info("Save transaction in database");
        return true;
    }

    public Transaction getTransaction(@NotNull String id) {
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public boolean deleteTransaction(@NotNull String id) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException(id);
        }
        transactionRepository.deleteById(id);
        return true;
    }

    public boolean updateTransaction(@NotNull String id, @NotNull Transaction transaction) {
        TransactionsValidator.validateTransactionEntity(transaction);
        if (transactionRepository.existsById(id)) {
            transaction.setTransactionId(id);
            transactionRepository.save(transaction);
            return true;
        }
        throw new TransactionNotFoundException(id);
    }

    public boolean saveTransactionsFromFile(@NotNull String operatorId, @NotNull MultipartFile multipartFile) {
        File f = multipartFileToFile(multipartFile);
        log.info("Saved transactions from file");
        saveTransactions(parseFile(f));
        return true;
    }

    private void saveTransactions(@NotNull List<Transaction> transactions) {
        for (Transaction t : transactions)
            saveTransaction(t);
    }

    private List<Transaction> parseFile(File f) {
        return parserFactory.getParser(getFileType(f)).parseTransactionsFile(f);
    }

    private String getFileType(File f) {
        String fileName = f.getName();
        if (!isValidFileExtension(fileName))
            throw new IllegalFileTypeException(pickOutExtension(fileName));
        return pickOutExtension(fileName);
    }

    private String pickOutExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private boolean isValidFileExtension(String fileName) {
        return (fileName.lastIndexOf(".") > 0);
    }

    private File multipartFileToFile(MultipartFile file) {
        try {
            log.info("MultipartFile was converted into file");
            return file.getResource().getFile();
        }
        catch (java.io.FileNotFoundException e) {
            throw new NoSuchFileException(file.getOriginalFilename());
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("File not found: ", e);
            throw new ParserException(e);
        }
    }

}

