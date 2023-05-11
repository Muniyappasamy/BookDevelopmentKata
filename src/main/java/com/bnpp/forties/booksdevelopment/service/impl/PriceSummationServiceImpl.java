package com.bnpp.forties.booksdevelopment.service.impl;

import com.bnpp.forties.booksdevelopment.exception.InvalidBookException;
import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.model.BookGroupClassification;
import com.bnpp.forties.booksdevelopment.model.CartSummaryReportDto;
import com.bnpp.forties.booksdevelopment.service.PriceSummationService;
import com.bnpp.forties.booksdevelopment.storerepository.BookDevelopmentStackDetails;
import com.bnpp.forties.booksdevelopment.storerepository.DiscountDetails;
import org.apache.commons.collections4.CollectionUtils;
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
    public CartSummaryReportDto getcartSummaryReport(List<BookDto> listOfBooks) {
        validateAllBooks(listOfBooks);
        Map<String, Integer> listOfBooksWithQuantityMap = listOfBooks.stream()
                .collect(Collectors.toMap(BookDto::getName, BookDto::getQuantity));
        List<BookGroupClassification> listOfBookGroup = getListOfBookGroupWithDiscount(listOfBooksWithQuantityMap, new ArrayList<>());
        BookGroupClassification booksWithoutDiscount = getListOfBookGroupWithoutDiscount(listOfBooksWithQuantityMap);
        listOfBookGroup.add(booksWithoutDiscount);
        double actualPrice = listOfBookGroup.stream().mapToDouble(BookGroupClassification::getActualPrice).sum();
        double discount = listOfBookGroup.stream().mapToDouble(BookGroupClassification::getDiscount).sum();
        CartSummaryReportDto cartSummaryReportDto = new CartSummaryReportDto();
        cartSummaryReportDto.setListOfBookGroupClassifications(listOfBookGroup);
        cartSummaryReportDto.setActualPrice(actualPrice);
        cartSummaryReportDto.setTotalDiscount(discount);
        cartSummaryReportDto.setBestPrice(actualPrice - discount);
        return cartSummaryReportDto;
    }
    private List<BookGroupClassification> getListOfBookGroupWithDiscount(Map<String, Integer> listOfBooksWithQuantityMap,
                                                                     List<BookGroupClassification> bookGroupClassificationList) {
        Optional<DiscountDetails> discount = getDiscount(listOfBooksWithQuantityMap.size());
        if (discount.isPresent()) {
            int bookGroupSize = discount.get().getNumberOfDistinctItems();
            List<String> listOfDistinctBooks = listOfBooksWithQuantityMap.keySet().stream().limit(bookGroupSize)
                    .collect(Collectors.toList());
            BookGroupClassification currentBookGroup = getBookGroup(listOfDistinctBooks);
            bookGroupClassificationList.add(currentBookGroup);
            removeDiscountedBooks(listOfBooksWithQuantityMap, listOfDistinctBooks);
            getListOfBookGroupWithDiscount(listOfBooksWithQuantityMap, bookGroupClassificationList);
        }
        return bookGroupClassificationList;
    }

    public void validateAllBooks(List<BookDto> listOfBooks) {
        Map<String, Double> validBooks = getValidBooks(); //
        List<String> invalidBooks = listOfBooks.stream().filter(book -> !validBooks.containsKey(book.getName())).map(BookDto::getName).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(invalidBooks)) {
            throw new InvalidBookException(invalidBooks);
        }
    }
    private Optional<DiscountDetails> getDiscount(int numberOfBooks) {

        return Arrays.stream(DiscountDetails.values()).filter(discountGroup -> discountGroup.getNumberOfDistinctItems() == numberOfBooks).findFirst();
    }

    private BookGroupClassification  getListOfBookGroupWithoutDiscount(Map<String, Integer> listOfBooksWithQuantityMap) {
        Map<String, Double> bookIdPriceMap = getValidBooks();
        Set<String> bookTitles = listOfBooksWithQuantityMap.keySet();
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
        Map<String, Double> bookIdPriceMap = getValidBooks();
        double actualPrice = listOfBookToGroup.stream().mapToDouble(bookId -> bookIdPriceMap.get(bookId) * ONE_QUANTITY)
                .sum();
        int discountPercentage = getDiscountPercentage(listOfBookToGroup.size());
        double discount = (actualPrice * discountPercentage) / HUNDRED;
        return new BookGroupClassification(discountPercentage, actualPrice, discount,listOfBookToGroup.size());

    }

    private Map<String, Double> getValidBooks() {
        return Arrays.stream(BookDevelopmentStackDetails.values())
                .collect(Collectors.toMap(BookDevelopmentStackDetails::getBookTitle, BookDevelopmentStackDetails::getPrice));
    }
    private int getDiscountPercentage(int numberOfDistinctBooks) {
        Optional<DiscountDetails> discount = getDiscount(numberOfDistinctBooks);
        return (discount.isPresent()) ? discount.get().getDiscountPercentage() : ZERO_PERCENT;
    }
}
