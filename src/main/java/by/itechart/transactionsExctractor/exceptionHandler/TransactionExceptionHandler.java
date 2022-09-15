package by.itechart.transactionsExctractor.exceptionHandler;

import by.itechart.transactionsExctractor.exceptions.parserExceptions.*;
import by.itechart.transactionsExctractor.exceptions.transactionExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler({NoSuchFileException.class, IllegalFileTypeException.class})
    public ResponseEntity<String> handleException(ParserException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
    }

    @ExceptionHandler(IllegalParsedValueException.class)
    public ResponseEntity<String> handleException(IllegalParsedValueException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }
    //Placeholder for all future service errors
    @ExceptionHandler({IllegalIdException.class, TransactionNotValidException.class, NoStoredTransactionsException.class, TransactionNotFoundException.class})
    public ResponseEntity<String> handleException(TransactionException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    //Exceptions thrown when nothing else fits, so Internal Server Error is the most logical?
    @ExceptionHandler({TransactionException.class, ParserException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
