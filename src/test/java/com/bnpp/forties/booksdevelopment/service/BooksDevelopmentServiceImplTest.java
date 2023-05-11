package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.model.Book;
import com.bnpp.forties.booksdevelopment.service.impl.BooksDevelopmentServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BooksDevelopmentServiceImplTest {
    private static final int TOTAL_NUMBER_OF_BOOKS = 5;

    @Autowired
    private BooksDevelopmentServiceImpl booksDevelopmentServiceImpl;

    @Test
    @DisplayName("Get All Books should return five  books")
    void getAllBooksShouldReturnAllFiveBooks() {
        List<Book> books = booksDevelopmentServiceImpl.getAllBooks();

        Assertions.assertThat(TOTAL_NUMBER_OF_BOOKS).isEqualTo(books.size());
    }
}