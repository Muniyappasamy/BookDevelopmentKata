package com.bnpp.forties.booksdevelopment.exception;

import java.util.List;

public class InvalidBookException extends RuntimeException {

    public InvalidBookException(List<String> invalidBooks) {
        super("Books which are given not in Book Store: " + invalidBooks);
    }
}
