package by.itechart.transactionsExctractor.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import by.itechart.transactionsExctractor.entity.Transaction;
import by.itechart.transactionsExctractor.entity.User;
import by.itechart.transactionsExctractor.exceptions.parserExceptions.IllegalParsedValueException;
import by.itechart.transactionsExctractor.exceptions.parserExceptions.ParserException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;

@Component("xml")
public class ParserXml implements Parser {
    @Override
    public List<Transaction> parseTransactionsFile(File file) {
        Document doc = convertToDocument(file);
        NodeList transactions = doc.getElementsByTagName("transaction");
        ArrayList<Transaction> resultTransactions = new ArrayList<>();

        for (int i = 0; i < transactions.getLength(); i++) {
            Node transaction = transactions.item(i);
            if (transaction.getNodeType() != Node.ELEMENT_NODE)
                continue;
            resultTransactions.add(parseTransaction(transaction.getChildNodes()));
        }

        return resultTransactions;
    }

    private Document convertToDocument(File file) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        } catch (Exception e) {
            throw new ParserException(e);
        }
    }

    private Transaction parseTransaction(NodeList transaction) {
        Transaction resultTransaction = new Transaction();
        for (int i = 0; i < transaction.getLength(); i++) {
            Node item = transaction.item(i);
            if (item.getNodeType() != Node.ELEMENT_NODE)
                continue;
            parseTransactionNode(item, resultTransaction);
        }

        return resultTransaction;
    }

    private void parseTransactionNode(Node node, Transaction transaction) {
        switch (node.getNodeName()) {
            case "id" -> parseTransactionID(getValue(node), transaction);
            case "user" -> parseTransactionUser(node.getChildNodes(), transaction);
            case "timestamp" -> parseTransactionTimestamp(getValue(node), transaction);
            case "payment_details" -> parseTransactionPaymentDetails(node.getChildNodes(), transaction);
            case "status" -> parseTransactionStatus(getValue(node), transaction);
        }
    }

    private void parseTransactionStatus(String status, Transaction transaction) {
        if (isFieldNull(status))
            throw new IllegalParsedValueException("Status field can not be null!");
        transaction.setStatus(status.equalsIgnoreCase("SUCCESS"));
    }

    private void parseTransactionID(String id, Transaction transaction) {
        if (isFieldNull(id))
            throw new IllegalParsedValueException("ID field can not be null!");
        transaction.setTransactionId(id);
    }

    private void parseTransactionTimestamp(String timestamp, Transaction transaction) {
        if (isFieldNull(timestamp))
            throw new IllegalParsedValueException("Timestamp field can not be null!");
        transaction.setCreationTime(timestamp);
    }

    private void parseTransactionUser(NodeList userFields, Transaction transaction) {
        transaction.setUser(new User());
        for (int i = 0; i < userFields.getLength(); i++) {
            Node item = userFields.item(i);
            if (item.getNodeType() != Node.ELEMENT_NODE)
                continue;
            parseUserNode(item, transaction);
        }
    }

    private void parseUserNode(Node node, Transaction transaction) {
        switch (node.getNodeName()) {
            case "id" -> parseUserId(getValue(node), transaction);
            case "first_name" -> transaction.getUser().setFirstName(getValue(node));
            case "last_name" -> transaction.getUser().setLastName(getValue(node));
            case "email" -> transaction.getUser().setEmail(getValue(node));
        }
    }

    private void parseUserId(String id, Transaction transaction) {
        if (isFieldNull(id))
            throw new IllegalParsedValueException("User Id field can not be null!");
        transaction.getUser().setId(id);
    }

    private void parseTransactionPaymentDetails(NodeList paymentDetails, Transaction transaction) {
        for (int i = 0; i < paymentDetails.getLength(); i++) {
            Node item = paymentDetails.item(i);
            if (item.getNodeType() != Node.ELEMENT_NODE)
                continue;
            parsePaymentDetailsNode(item, transaction);
        }
    }

    private void parsePaymentDetailsNode(Node node, Transaction transaction) {
        switch (node.getNodeName()) {
            case "amount" -> parseAmount(getValue(node), transaction);
            case "currency" -> parseCurrency(getValue(node), transaction);
        }
    }

    private void parseAmount(String amount, Transaction transaction) {
        amount = String.join("", amount.split(" "));
        if (isFieldNull(amount) || isInvalidAmountValue(amount))
            throw new IllegalParsedValueException("Amount " + amount + " is illegal!");
        transaction.setAmount(Long.parseLong(amount));
    }

    private void parseCurrency(String currency, Transaction transaction) {
        if (isFieldNull(currency))
            throw new IllegalParsedValueException("Currency field can not be null!");
        transaction.setCurrency(currency);
    }

    private boolean isFieldNull(String field) { return field == null; }

    private boolean isInvalidAmountValue(String value) { return !value.matches("\\d+"); }

    private String getValue(Node node) { return node.getFirstChild().getNodeValue(); }
}
