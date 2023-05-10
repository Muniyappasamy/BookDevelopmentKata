package com.bnpp.forties.booksdevelopment.controller;


import com.bnpp.forties.booksdevelopment.model.Book;
import com.bnpp.forties.booksdevelopment.service.BooksDevelopmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/booksdevelopment")
public class BooksDevelopmentController {


    @Autowired
    BooksDevelopmentService booksDevelopmentService;

    @GetMapping("/getallbooks")
    public List<Book> getAllBooks() {
        return booksDevelopmentService.getAllBooks();
    }


}
