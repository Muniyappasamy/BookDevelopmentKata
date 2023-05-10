package com.bnpp.forties.booksdevelopment.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booksdevelopment")
public class BooksDevelopmentController {

    @GetMapping("/getallbooks")
    public String getAllBooks() {
        return "All Books";
    }


}
