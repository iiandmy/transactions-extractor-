package by.itechart.transactionsExctractor;

import by.itechart.transactionsExctractor.parser.ParserCsv;
import by.itechart.transactionsExctractor.parser.ParserXml;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ParserUnitTests {

    @Autowired
    private ParserXml parserXml;

    @Autowired
    private ParserCsv parserCsv;

    @Test
    public void whenParse_returnsTransactionsList() {
        File validXMLFile = new File("src/test/java/by/itechart/transactionsExctractor/testingFiles/xml_test_valid.xml");
        File validCSVFile = new File("src/test/java/by/itechart/transactionsExctractor/testingFiles/csv_test_valid.csv");
        assertThat(parserXml.parseTransactionsFile(validXMLFile).size()).isEqualTo(5);
        assertThat(parserCsv.parseTransactionsFile(validCSVFile).size()).isEqualTo(5);
    }

}
