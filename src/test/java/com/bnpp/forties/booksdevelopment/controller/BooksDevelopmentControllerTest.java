package com.bnpp.forties.booksdevelopment.controller;

import com.bnpp.forties.booksdevelopment.model.Book;
import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.service.impl.BooksDevelopmentServiceImpl;
import com.bnpp.forties.booksdevelopment.service.impl.PriceSummationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BooksDevelopmentController.class)
class BooksDevelopmentControllerTest {

    @Autowired
    private BooksDevelopmentController booksDevelopmentController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BooksDevelopmentServiceImpl booksDevelopmentServiceImpl;

    @MockBean
    private PriceSummationServiceImpl priceSummationServiceImpl;


    @Test
    @DisplayName("BooksDevelopment controller instance should not be null")
    void booksDevelopmentControllershouldNotBeNull() {
        Assertions.assertThat(booksDevelopmentController).isNotNull();
    }

    @Test
    @DisplayName(" API getBooks should return status OK")
    void getBooksApiShouldReturnStatusOK() throws Exception {
        mockMvc.perform(get("/api/developmentbooks/getallbooks")).andExpect(status().isOk());
    }


    @Test
    @DisplayName(" API calculate discount price should return status OK")
    void calculateDiscountPriceApiShouldReturnStatusOK() throws Exception {
        List<BookDto> listOfBooks = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code", 2);
        BookDto bookDto2 = new BookDto("The Clean Coder", 1);

        listOfBooks.add(bookDto1);
        listOfBooks.add(bookDto2);

        mockMvc.perform(post("/api/developmentbooks/calculatediscountprice").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(listOfBooks))).andExpect(status().isOk());
    }


}