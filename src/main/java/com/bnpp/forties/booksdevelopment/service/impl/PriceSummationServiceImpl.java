package com.bnpp.forties.booksdevelopment.service.impl;

import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.service.PriceSummationService;
import com.bnpp.forties.booksdevelopment.storerepository.BookDevelopmentStackDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PriceSummationServiceImpl implements PriceSummationService {
    @Override
    public Double calculatePrice(List<BookDto> books) {
        Map<String, Double> bookTitlePriceMap = Arrays.stream(BookDevelopmentStackDetails.values())
                .collect(Collectors.toMap(BookDevelopmentStackDetails::getBookTitle, BookDevelopmentStackDetails::getPrice));
        Map<String, Integer> listOfBooksWithQuantityMap = books.stream()
                .collect(Collectors.toMap(BookDto::getName, BookDto::getQuantity));
        Set<String> uniqueBooks = listOfBooksWithQuantityMap.keySet();
        long distinctBooks = books.stream().map(BookDto::getName).distinct().count();
        int discountPercentage = (distinctBooks == 2) ? 5 : 0;
        discountPercentage = (distinctBooks == 3) ? 10 : discountPercentage;

        double actualPrice = uniqueBooks.stream().mapToDouble(bookName -> bookTitlePriceMap.get(bookName) * listOfBooksWithQuantityMap.get(bookName)).sum();
        double discountedPrice = (actualPrice * discountPercentage) / 100;

        return actualPrice-discountedPrice;
    }
}
