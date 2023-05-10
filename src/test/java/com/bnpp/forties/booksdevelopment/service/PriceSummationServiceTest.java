package com.bnpp.forties.booksdevelopment.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PriceSummationServiceTest {

    private static final String BOOK_NAME = "Clean Code";
    private static final double BOOK_PRICE = 50.00;

    @Autowired
    private PriceSummationService priceSummationService;

    @Test
    @DisplayName("50 should return for a single book")
    void priceForABook_shouldReturnFifty() {
        Double actualPrice = priceSummationService.calculatePrice(BOOK_NAME);

        assertEquals(BOOK_PRICE, actualPrice);
    }
}
