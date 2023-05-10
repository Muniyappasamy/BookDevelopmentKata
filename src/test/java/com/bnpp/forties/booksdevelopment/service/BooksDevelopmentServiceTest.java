package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BooksDevelopmentServiceTest {
    private static final int TOTAL_NUMBER_OF_BOOKS = 5;

    @Autowired
    private BooksDevelopmentService booksDevelopmentService;

    @Test
    @DisplayName("Get All Books should return five  books")
    void getAllBooks_ShouldReturn_AllFiveBooks() {
        List<Book> books = booksDevelopmentService.getAllBooks();

        assertEquals(TOTAL_NUMBER_OF_BOOKS, books.size(), "All 5 Books are Received");
    }
}