package by.itechart.transactionsExctractor.controller;

import by.itechart.transactionsExctractor.service.FileStorageService;
import by.itechart.transactionsExctractor.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;


@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TransactionController {

    private final FileStorageService fileStorageService;

    private final TransactionsService transactionsService;

    @PostMapping("/uploadFiles/{operatorId}")
    public ResponseEntity<String> uploadMultipleFiles(@PathVariable("operatorId") String operatorId, @RequestParam("files") MultipartFile[] files) {
        Arrays.asList(files).stream().map((file) -> {
            transactionsService.saveTransactionsFromFile(operatorId, file);
            return fileStorageService.storeFile(file);
        });
        return ResponseEntity.ok("Files was uploaded");
    }
}
