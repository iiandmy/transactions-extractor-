package by.itechart.transactionsExctractor;

import by.itechart.transactionsExctractor.service.TransactionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UploadFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionsService transactionsService;

    @Test
    public void whenSendUploadMultipleRequest_getResponseOK() throws Exception {
        mockMvc.perform(
                multipart("/uploadFiles")
                        .file(new MockMultipartFile("xml_test_valid.xml", new FileInputStream("src/test/java/by/itechart/transactionsExctractor/testingFiles/xml_test_valid.xml")))
                        .file(new MockMultipartFile("csv_test_valid.xml", new FileInputStream("src/test/java/by/itechart/transactionsExctractor/testingFiles/csv_test_valid.csv")))
        ).andExpect(status().isOk());
        deleteExtras();
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
