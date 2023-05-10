package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.model.Book;
import com.bnpp.forties.booksdevelopment.storerepository.BookDevelopmentStackDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksDevelopmentService {

    public List<Book> getAllBooks() {
        return Arrays.stream(BookDevelopmentStackDetails.values()).map(bookStackEnum -> new Book(bookStackEnum.getBookTitle(),
                        bookStackEnum.getAuthor(), bookStackEnum.getPrice()))
                .collect(Collectors.toList());

    }
}
