package by.itechart.transactionsExctractor.parser;

import by.itechart.transactionsExctractor.entity.*;

import java.io.File;
import java.util.List;

public interface Parser {
    public List<Transaction> parseTransactionsFile(File f);
}
