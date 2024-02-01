package com.fatema.sample.exception_handling;
import com.fatema.sample.web.api.v1.dto.response.BaseResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(StatementException.class)
    public ResponseEntity<?> handleStatementException(StatementException ex) {
        return BaseResponse.responseBuilder(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,null);
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(ChangeSetPersister.NotFoundException ex) {

        return BaseResponse.responseBuilder(ex.getMessage(), HttpStatus.NOT_FOUND,null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<?> handleInvalidDateFormatException(InvalidDateFormatException ex) {
        return  BaseResponse.responseBuilder(ex.getMessage(), HttpStatus.BAD_REQUEST,null);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<?> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {

        return BaseResponse.responseBuilder(ex.getMessage(), HttpStatus.UNAUTHORIZED,null);

    }
}