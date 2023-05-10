package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.model.BookDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto = new BookDto("Clean Code",1);
        books.add(bookDto);
        Double actualPrice = priceSummationService.calculatePrice(books);

        assertEquals(BOOK_PRICE, actualPrice);
    }
}
