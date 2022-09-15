package by.itechart.transactionsExctractor.parser;

import by.itechart.transactionsExctractor.entity.Transaction;
import by.itechart.transactionsExctractor.entity.User;
import by.itechart.transactionsExctractor.exceptions.parserExceptions.IllegalParsedValueException;
import by.itechart.transactionsExctractor.exceptions.parserExceptions.ParserException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component("csv")
public class ParserCsv implements Parser {
    private final int TIMESTAMP = 0;
    private final int TRANSACTION_ID = 1;
    private final int USER_ID = 2;
    private final int AMOUNT = 3;
    private final int CURRENCY = 4;
    private final int STATUS = 5;

    @Override
    public List<Transaction> parseTransactionsFile(File file) {
        List<Transaction> transactions = new ArrayList<>();
        List<String> rows = readFile(file);
        rows.remove(0);

        for (String row : rows) {
            transactions.add(parseCSVLine(row));
        }

        return transactions;
    }

    private List<String> readFile(File f) {
        try {
            return Files.readAllLines(Path.of(f.getPath()));
        } catch (IOException e) {
            throw new ParserException(e);
        }
    }

    private Transaction parseCSVLine(String line) {
        String[] columns = line.split(",");
        return new Transaction(
                validateField("Id", columns[TRANSACTION_ID]),
                validateUserId(columns[USER_ID]),
                validateField("Timestamp", columns[TIMESTAMP]),
                validateAmount(columns[AMOUNT]),
                validateField("Currency", columns[CURRENCY]),
                columns[STATUS].equalsIgnoreCase("SUCCESS")
        );
    }

    private String validateField(String fieldName, String fieldValue) {
        if (isFieldNull(fieldValue))
            throw new IllegalParsedValueException(fieldName + " field can not be null!");
        return fieldValue;
    }

    private User validateUserId(String userId) {
        if (isFieldNull(userId))
            throw new IllegalParsedValueException("UserId field can not be null!");
        return new User(userId);
    }

    private Long validateAmount(String amount) {
        if (isFieldNull(amount) || isInvalidLongValue(amount))
            throw new IllegalParsedValueException("Amount " + amount + " is illegal!");
        return Long.parseLong(amount);
    }

    private boolean isFieldNull(String field) { return field == null; }

    private boolean isInvalidLongValue(String value) { return !value.matches("\\d+"); }
}