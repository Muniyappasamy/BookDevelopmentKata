package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.storerepository.BookDevelopmentStackDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PriceSummationService {

    public Double calculatePrice(String bookTitle) {
        Map<String, Double> bookTitlePriceMap = Arrays.stream(BookDevelopmentStackDetails.values())
                .collect(Collectors.toMap(BookDevelopmentStackDetails::getBookTitle, BookDevelopmentStackDetails::getPrice));
        return bookTitlePriceMap.get(bookTitle);
    }
}
