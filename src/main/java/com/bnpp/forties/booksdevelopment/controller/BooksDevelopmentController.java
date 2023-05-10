package com.bnpp.forties.booksdevelopment.controller;


import com.bnpp.forties.booksdevelopment.model.Book;
import com.bnpp.forties.booksdevelopment.service.BooksDevelopmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = BooksDevelopmentController.APIRoutes.BooksController.ROOT_API)
@RequiredArgsConstructor
public class BooksDevelopmentController {

    private final BooksDevelopmentService booksDevelopmentService;

    @RequestMapping(method = RequestMethod.GET, value = APIRoutes.BooksController.GET_ALL_BOOKS)
    public List<Book> getAllBooks() {
        return booksDevelopmentService.getAllBooks();
    }

    public class APIRoutes {

        public class BooksController {
            public static final String ROOT_API = "/api/booksdevelopment";
            public static final String GET_ALL_BOOKS = "/getallbooks";
        }
    }

}

