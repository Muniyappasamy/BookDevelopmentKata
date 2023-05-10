package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.model.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PriceSummationService {
    public Double calculatePrice(List<BookDto> books);
}
