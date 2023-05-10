package com.bnpp.forties.booksdevelopment.service.impl;

import com.bnpp.forties.booksdevelopment.model.Book;
import com.bnpp.forties.booksdevelopment.model.BookMapper;
import com.bnpp.forties.booksdevelopment.service.BooksDevelopmentService;
import com.bnpp.forties.booksdevelopment.storerepository.BookDevelopmentStackDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksDevelopmentServiceImpl implements BooksDevelopmentService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> getAllBooks() {
        return Arrays.stream(BookDevelopmentStackDetails.values()).map(bookStackEnum -> bookMapper.mapper(bookStackEnum))
                .collect(Collectors.toList());

    }
}
