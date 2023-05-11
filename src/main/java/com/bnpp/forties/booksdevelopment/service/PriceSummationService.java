package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.model.CartSummaryReportDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PriceSummationService {
    public CartSummaryReportDto calculatePrice(List<BookDto> books);
}
