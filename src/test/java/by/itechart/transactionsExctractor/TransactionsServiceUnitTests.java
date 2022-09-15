package by.itechart.transactionsExctractor;

import by.itechart.transactionsExctractor.commands.implementation.GetMaximalCommand;
import by.itechart.transactionsExctractor.commands.implementation.GetMinimalCommand;
import by.itechart.transactionsExctractor.entity.Transaction;
import by.itechart.transactionsExctractor.entity.User;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.NoStoredTransactionsException;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.TransactionNotValidException;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.TransactionNotFoundException;
import by.itechart.transactionsExctractor.service.TransactionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TransactionsServiceUnitTests {

    @Autowired
    private TransactionsService transactionsService;

    @Test
    public void fullCrudCycleWithValidTransaction_Test() {
        Transaction validTransaction = getValidTransaction();

        assertThat(transactionsService.saveTransaction(validTransaction)).isTrue();
        validTransaction.setAmount(0L);
        assertThat(transactionsService.updateTransaction(validTransaction.getTransactionId(), validTransaction)).isTrue();
        assertThat(transactionsService.getTransaction(validTransaction.getTransactionId())).isEqualTo(validTransaction);
        assertThat(transactionsService.deleteTransaction(validTransaction.getTransactionId())).isTrue();

        assertThatExceptionOfType(TransactionNotFoundException.class).isThrownBy(() -> transactionsService.getTransaction(validTransaction.getTransactionId()));
    }

    @Test
    public void ifSaveNullOrInvalidTransaction_thenThrowException() {
        Transaction invalidTransaction = getInvalidTransaction();

        assertThatExceptionOfType(TransactionNotValidException.class).isThrownBy(() -> transactionsService.saveTransaction(invalidTransaction));
        assertThatExceptionOfType(TransactionNotValidException.class).isThrownBy(() -> transactionsService.saveTransaction(null));
    }

    @Test //parameterized???
    public void ifGetMinOrMaxWhileEmpty_thenThrowException() {
        Transaction takenOutTemp = transactionsService.getTransaction("11111111-8251-404e-8c3f-73b70bd0ec80");
        transactionsService.deleteTransaction(takenOutTemp.getTransactionId());
        assertThatExceptionOfType(NoStoredTransactionsException.class).isThrownBy(() -> transactionsService.getMinOrMax("GET_MINIMAL"));
        assertThatExceptionOfType(NoStoredTransactionsException.class).isThrownBy(() -> transactionsService.getMinOrMax("GET_MAXIMAL"));
        transactionsService.saveTransaction(takenOutTemp);
    }

    @Test
    public void ifGetByInvalidOrNonExistentId_thenThrowException() {
        assertThatExceptionOfType(TransactionNotFoundException.class).isThrownBy(() -> transactionsService.getTransaction("12345678-8251-404e-8c3f-73b70bd0ec80"));
    }

    @Test
    public void ifDeleteByInvalidId_thenThrowException() {
        assertThatExceptionOfType(TransactionNotFoundException.class).isThrownBy(() -> transactionsService.deleteTransaction("12345678-8251-404e-8c3f-73b70bd0ec80"));
    }

    @Test
    public void ifUpdateByInvalidIdOrInvalidTransaction_thenThrowException() {
        assertThatExceptionOfType(TransactionNotValidException.class).isThrownBy(() -> transactionsService.updateTransaction("11111111-8251-404e-8c3f-73b70bd0ec80", getInvalidTransaction()));
        assertThatExceptionOfType(TransactionNotValidException.class).isThrownBy(() -> transactionsService.updateTransaction("11111111-8251-404e-8c3f-73b70bd0ec80", null));
    }

    @Test
    public void ifUpdateByNonExistentId_thenThrowException() {
        assertThatExceptionOfType(TransactionNotFoundException.class).isThrownBy(() -> transactionsService.updateTransaction("12345678-8251-404e-8c3f-73b70bd0ec80", getValidTransaction()));
    }

//    @Test //fixme
//    public void ifParseNonExistentOrWrongExtensionFile_thenThrowException() throws IOException {
//        MockMultipartFile wrongExtensionFile = new MockMultipartFile("wrong_extension_test_file.txt", new FileInputStream("src/test/java/by/itechart/transactionsExctractor/testingFiles/wrong_extension_test_file.txt"));
//        assertThatExceptionOfType(IllegalFileTypeException.class).isThrownBy(() -> transactionsService.saveTransactionsFromFile(wrongExtensionFile));
//    }

//    @Test //fixme
//    public void ifParseValidFile_thenReturnTrue() throws IOException {
//        MockMultipartFile validXMLFile = new MockMultipartFile("xml_test_valid.xml", new FileInputStream("src/test/java/by/itechart/transactionsExctractor/testingFiles/xml_test_valid.xml"));
//        MockMultipartFile validCSVFile = new MockMultipartFile("csv_test_valid.xml", new FileInputStream("src/test/java/by/itechart/transactionsExctractor/testingFiles/csv_test_valid.csv"));
//        assertThat(transactionsService.saveTransactionsFromFile(validXMLFile)).isTrue();
//        assertThat(transactionsService.saveTransactionsFromFile(validCSVFile)).isTrue();
//        deleteExtras(); //Temp until H2
//    }

//    @Test /fixme
//    public void whenGetTopFive_thenReturnFiveTransactions() throws IOException {
//        MockMultipartFile validXMLFile = new MockMultipartFile("xml_test_valid.xml", new FileInputStream("src/test/java/by/itechart/transactionsExctractor/testingFiles/xml_test_valid.xml"));
//        assertThat(transactionsService.saveTransactionsFromFile(validXMLFile)).isTrue();
//        assertThat(transactionsService.getTopFiveTransactions().size()).isEqualTo(5);
//        deleteExtras(); //Temp until H2
//    }

//    @Test //fixme
//    public void whenGetMinAndMaxTransaction_thenReturnMinAndMaxByAmount() throws IOException {
//        MockMultipartFile validXMLFile = new MockMultipartFile("xml_test_valid.xml", new FileInputStream("src/test/java/by/itechart/transactionsExctractor/testingFiles/xml_test_valid.xml"));
//        assertThat(transactionsService.saveTransactionsFromFile(validXMLFile)).isTrue();
//        assertThat(transactionsService.getMaxTransaction().getAmount()).isEqualTo(35000);
//        assertThat(transactionsService.getMinTransaction().getAmount()).isEqualTo(2222);
//        deleteExtras(); //Temp until H2
//    }

    @Test //TODO
    public void whenGetStatistic_thenReturnFullStatistic() {
        assertThat(transactionsService.getStatistic()).isNotBlank();
    }

    private Transaction getValidTransaction() {
        return new Transaction(
                "22222222-8251-404e-8c3f-73b70bd0ec80",
                new User("22222222-3a72-454d-865d-8f6818f8dd62"),
                "2021-02-22 22:22:22",
                99999L,
                "usd",
                true
        );
    }

    private Transaction getInvalidTransaction() {
        return new Transaction(
                null,
                null,
                null,
                -1,
                null,
                false
        );
    }

    private void deleteExtras() {
        transactionsService.deleteTransaction("22222222-8251-404e-8c3f-73b70bd0ec80");
        transactionsService.deleteTransaction("33333333-03f1-4a87-a1f2-dee732a8b754");
        transactionsService.deleteTransaction("44444444-8608-41cd-9148-b604f4f1c5a4");
        transactionsService.deleteTransaction("55555555-c8e6-4ea2-8a02-e5e2bf5a21c7");
        transactionsService.deleteTransaction("66666666-5e69-481b-95c1-28cc9d05cb94");
        transactionsService.deleteTransaction("22222222-8157-4099-871f-ffd6225b5c76");
        transactionsService.deleteTransaction("33333333-225a-4966-a7f1-fd93d1357d5e");
        transactionsService.deleteTransaction("44444444-225a-4966-a7f1-fd93d1357d5e");
        transactionsService.deleteTransaction("55555555-225a-4966-a7f1-fd93d1357d5e");
        transactionsService.deleteTransaction("66666666-225a-4966-a7f1-fd93d1357d5e");
    }

}
