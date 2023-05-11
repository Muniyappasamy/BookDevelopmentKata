package com.bnpp.forties.booksdevelopment.exception;

import com.bnpp.forties.booksdevelopment.model.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends Exception {

    private static final String INVALID_BOOK_ERROR_CODE = "BOOK_00000";
    private static final String INVALID_BOOK_QUANTITY_ERROR_CODE = "BOOK_00001";

    @ExceptionHandler({InvalidBookException.class})
    public ResponseEntity<Object> handleBookTitleBadRequestException(InvalidBookException ex, WebRequest request) {
        ApiException apiException = new ApiException(INVALID_BOOK_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<Object>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidQuantityException.class})
    public ResponseEntity<Object> handleBookQuantityBadRequestException(InvalidQuantityException ex,WebRequest request) {
        ApiException apiException = new ApiException(INVALID_BOOK_QUANTITY_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<Object>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
