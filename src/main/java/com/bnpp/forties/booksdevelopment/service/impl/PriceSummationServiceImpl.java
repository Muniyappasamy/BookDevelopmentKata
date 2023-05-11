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

    private static final int ONE_QUANTITY = 1;


    private static final int HUNDRED = 100;

    @Override
    public Double calculatePrice(List<BookDto> books) {
        Map<String, Double> bookTitlePriceMap = Arrays.stream(BookDevelopmentStackDetails.values())
                .collect(Collectors.toMap(BookDevelopmentStackDetails::getBookTitle, BookDevelopmentStackDetails::getPrice));
        Map<String, Integer> listOfBooksWithQuantityMap = books.stream()
                .collect(Collectors.toMap(BookDto::getName, BookDto::getQuantity));
       // Set<String> uniqueBooks = listOfBooksWithQuantityMap.keySet();

        int discountGroup = listOfBooksWithQuantityMap.size();
        List<String> bookGroups = listOfBooksWithQuantityMap.keySet().stream().limit(discountGroup)
                .collect(Collectors.toList());
        double actualPriceforDiscountedBooks = bookGroups.stream()
                .mapToDouble(bookName -> bookTitlePriceMap.get(bookName)).sum();
        double discountedPrice = (actualPriceforDiscountedBooks * getDiscountPercentage(discountGroup)) / HUNDRED;
        bookGroups.forEach(bookName -> {
            int quantity = listOfBooksWithQuantityMap.get(bookName);
            if (quantity > ONE_QUANTITY) {
                listOfBooksWithQuantityMap.put(bookName, quantity - ONE_QUANTITY);
            } else {
                listOfBooksWithQuantityMap.remove(bookName);
            }
        });
        double discountedBookPrice = actualPriceforDiscountedBooks - discountedPrice;
        Set<String> remainingBooks = listOfBooksWithQuantityMap.keySet();
        double priceForRemainingBooks = remainingBooks.stream()
                .mapToDouble(title -> listOfBooksWithQuantityMap.get(title) * bookTitlePriceMap.get(title)).sum();

        return (discountedBookPrice + priceForRemainingBooks);
    }

    private int getDiscountPercentage(long numberOfDistinctItems) {
        Optional<DiscountDetails> discount = Arrays.stream(DiscountDetails.values())
                .sorted(Comparator.reverseOrder())
                .filter(discountGroup -> discountGroup.getNumberOfDistinctItems() <= numberOfDistinctItems).findFirst();
        return (discount.isPresent()) ? discount.get().getDiscountPercentage() : ZERO_PERCENT;
    }
}
