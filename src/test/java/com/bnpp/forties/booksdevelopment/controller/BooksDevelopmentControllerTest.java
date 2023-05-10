package com.bnpp.forties.booksdevelopment.controller;

import com.bnpp.forties.booksdevelopment.service.BooksDevelopmentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BooksDevelopmentController.class)
class BooksDevelopmentControllerTest {

    @Autowired
    private BooksDevelopmentController booksDevelopmentController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BooksDevelopmentService booksDevelopmentService;

    @Test
    @DisplayName("BooksDevelopment controller instance should not be null")
    void booksDevelopmentControllershouldNotBeNull() {
        Assertions.assertThat(booksDevelopmentController).isNotNull();
    }

    @Test
    @DisplayName(" API getBooks should return status OK")
    void getBooks_Api_shouldReturn_StatusOK() throws Exception {
        mockMvc.perform(get("/api/booksdevelopment/getallbooks")).andExpect(status().isOk());
    }

}