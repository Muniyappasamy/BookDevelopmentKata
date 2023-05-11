package com.bnpp.forties.booksdevelopment.exception;

import com.bnpp.forties.booksdevelopment.model.ApiException;

import java.util.List;

public class InvalidBookException extends RuntimeException {

    private static final String INVALID_BOOK_ERROR_CODE= "BOOK_00000";
    public InvalidBookException(List<String> invalidBooks) {
        super("Books which are given is not available in Book Store: " + invalidBooks);
    }
}
