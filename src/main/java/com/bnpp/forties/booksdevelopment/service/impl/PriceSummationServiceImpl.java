package com.bnpp.forties.booksdevelopment.service.impl;

import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.model.BookGroupClassification;
import com.bnpp.forties.booksdevelopment.service.PriceSummationService;
import com.bnpp.forties.booksdevelopment.storerepository.BookDevelopmentStackDetails;
import com.bnpp.forties.booksdevelopment.storerepository.DiscountDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        int discountGroup = listOfBooksWithQuantityMap.size();
        List<String> bookGroups = listOfBooksWithQuantityMap.keySet().stream().limit(discountGroup)
                .collect(Collectors.toList());
        double actualPriceforDiscountedBooks = bookGroups.stream()
                .mapToDouble(bookName -> bookTitlePriceMap.get(bookName)).sum();
        double discountedPrice = (actualPriceforDiscountedBooks * getDiscountPercentage(discountGroup)) / HUNDRED;
        removeDiscountedBooks(listOfBooksWithQuantityMap,bookGroups);
        double discountedBookPrice = actualPriceforDiscountedBooks - discountedPrice;
        Set<String> remainingBooks = listOfBooksWithQuantityMap.keySet();
        double priceForRemainingBooks = remainingBooks.stream()
                .mapToDouble(title -> listOfBooksWithQuantityMap.get(title) * bookTitlePriceMap.get(title)).sum();
        return (discountedBookPrice + priceForRemainingBooks);
    }
    private List<BookGroupClassification> getListOfBookGroupDiscount(Map<String, Integer> listOfBooksWithQuantityMap,
                                                                     List<BookGroupClassification> bookGroupClassificationList) {
        Optional<DiscountDetails> discount = getDiscount(listOfBooksWithQuantityMap.size());
        if (discount.isPresent()) {
            int bookGroupSize = discount.get().getNumberOfDistinctItems();
            List<String> listOfDistinctBooks = listOfBooksWithQuantityMap.keySet().stream().limit(bookGroupSize)
                    .collect(Collectors.toList());
            BookGroupClassification currentBookGroup = getBookGroup(listOfDistinctBooks);
            bookGroupClassificationList.add(currentBookGroup);
            removeDiscountedBooks(listOfBooksWithQuantityMap, listOfDistinctBooks);
            getListOfBookGroupDiscount(listOfBooksWithQuantityMap, bookGroupClassificationList);
        }
        return bookGroupClassificationList;
    }
    private Optional<DiscountDetails> getDiscount(int numberOfBooks) {

        return Arrays.stream(DiscountDetails.values()).filter(discountGroup -> discountGroup.getNumberOfDistinctItems() == numberOfBooks).findFirst();
    }

    private BookGroupClassification  getListOfBookGroupWithoutDiscount(Map<Integer, Integer> listOfBooksWithQuantityMap) {
        Map<String, Double> bookIdPriceMap = getBookIdPriceMap();
        Set<Integer> bookTitles = listOfBooksWithQuantityMap.keySet();
        double actualPrice = bookTitles.stream()
                .mapToDouble(bookId -> bookIdPriceMap.get(bookId) * listOfBooksWithQuantityMap.get(bookId)).sum();
        int numberOfBooks = listOfBooksWithQuantityMap.values().stream().mapToInt(Integer::intValue).sum();
        return new BookGroupClassification( ZERO_PERCENT,  actualPrice, BigDecimal.ZERO.doubleValue(), numberOfBooks);
    }

    private void removeDiscountedBooks(Map<String, Integer> listOfBooksWithQuantityMap, List<String> discountedBooks) {
        discountedBooks.forEach(bookName -> {
            int quantity = listOfBooksWithQuantityMap.get(bookName);
            if (quantity > ONE_QUANTITY) {
                listOfBooksWithQuantityMap.put(bookName, quantity - ONE_QUANTITY);
            } else {
                listOfBooksWithQuantityMap.remove(bookName);
            }
        });
    }



    private BookGroupClassification getBookGroup(List<String> listOfBookToGroup) {
        Map<String, Double> bookIdPriceMap = getBookIdPriceMap();
        double actualPrice = listOfBookToGroup.stream().mapToDouble(bookId -> bookIdPriceMap.get(bookId) * ONE_QUANTITY)
                .sum();
        int discountPercentage = getDiscountPercentage(listOfBookToGroup.size());
        double discount = (actualPrice * discountPercentage) / HUNDRED;
        return new BookGroupClassification(discountPercentage, actualPrice, discount,listOfBookToGroup.size());

    }

    private Map<String, Double> getBookIdPriceMap() {
        return Arrays.stream(BookDevelopmentStackDetails.values())
                .collect(Collectors.toMap(BookDevelopmentStackDetails::getBookTitle, BookDevelopmentStackDetails::getPrice));
    }
    private int getDiscountPercentage(int numberOfDistinctBooks) {
        Optional<DiscountDetails> discount = getDiscount(numberOfDistinctBooks);
        return (discount.isPresent()) ? discount.get().getDiscountPercentage() : ZERO_PERCENT;
    }
}
