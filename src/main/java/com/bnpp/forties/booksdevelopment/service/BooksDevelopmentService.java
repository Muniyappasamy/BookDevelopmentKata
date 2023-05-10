package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BooksDevelopmentService {

    public List<Book> getAllBooks();
}
