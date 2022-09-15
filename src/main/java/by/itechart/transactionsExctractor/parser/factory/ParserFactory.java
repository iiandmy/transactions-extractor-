package by.itechart.transactionsExctractor.parser.factory;

import java.util.Map;

import by.itechart.transactionsExctractor.exceptions.parserExceptions.IllegalFileTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import by.itechart.transactionsExctractor.parser.Parser;

@Component
@RequiredArgsConstructor
public class ParserFactory {
    private final Map<String, Parser> parserMap;

    public Parser getParser(String parserName) {
        if (!parserMap.containsKey(parserName))
            throw new IllegalFileTypeException("Invalid parser type");
        return parserMap.get(parserName.toLowerCase());
    }
}
