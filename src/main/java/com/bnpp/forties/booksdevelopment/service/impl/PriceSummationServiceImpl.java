package com.bnpp.forties.booksdevelopment.service.impl;

import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.service.PriceSummationService;
import com.bnpp.forties.booksdevelopment.storerepository.BookDevelopmentStackDetails;
import com.bnpp.forties.booksdevelopment.storerepository.DiscountDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceSummationServiceImpl implements PriceSummationService {


    private static final int ZERO_PERCENT = 0;


    private static final int HUNDRED = 100;

    @Override
    public Double calculatePrice(List<BookDto> books) {
        Map<String, Double> bookTitlePriceMap = Arrays.stream(BookDevelopmentStackDetails.values())
                .collect(Collectors.toMap(BookDevelopmentStackDetails::getBookTitle, BookDevelopmentStackDetails::getPrice));
        Map<String, Integer> listOfBooksWithQuantityMap = books.stream()
                .collect(Collectors.toMap(BookDto::getName, BookDto::getQuantity));
        Set<String> uniqueBooks = listOfBooksWithQuantityMap.keySet();
        long distinctBooks = books.stream().map(BookDto::getName).distinct().count();

        double actualPrice = uniqueBooks.stream().mapToDouble(bookName -> bookTitlePriceMap.get(bookName) * listOfBooksWithQuantityMap.get(bookName)).sum();
        double discountedPrice = (actualPrice * getDiscountPercentage(distinctBooks)) / HUNDRED;

        return actualPrice - discountedPrice;
    }

    private int getDiscountPercentage(long numberOfDistinctItems) {
        Optional<DiscountDetails> discount = Arrays.stream(DiscountDetails.values())
                .sorted(Comparator.reverseOrder())
                .filter(discountGroup -> discountGroup.getNumberOfDistinctItems() <= numberOfDistinctItems).findFirst();
        return (discount.isPresent()) ? discount.get().getDiscountPercentage() : ZERO_PERCENT;
    }
}
