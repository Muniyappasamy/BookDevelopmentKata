package com.bnpp.forties.booksdevelopment.service.impl;

import com.bnpp.forties.booksdevelopment.exception.InvalidBookException;
import com.bnpp.forties.booksdevelopment.exception.InvalidQuantityException;
import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.model.BookGroupClassification;
import com.bnpp.forties.booksdevelopment.model.CartSummaryReportDto;
import com.bnpp.forties.booksdevelopment.service.PriceSummationService;
import com.bnpp.forties.booksdevelopment.storerepository.BookStoreEnum;
import com.bnpp.forties.booksdevelopment.storerepository.DiscountDetailsEnum;
import com.bnpp.forties.booksdevelopment.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceSummationServiceImpl implements PriceSummationService {


    @Override
    public CartSummaryReportDto getcartSummaryReport(List<BookDto> listOfBooks) {
        validateAllBooks(listOfBooks);
        Map<String, Integer> listOfBooksWithQuantityMap = listOfBooks.stream()
                .collect(Collectors.toMap(BookDto::getName, BookDto::getQuantity));
        List<Integer> listOfPossibleDiscounts = getPossibleDiscountValues(listOfBooksWithQuantityMap.size());
        CartSummaryReportDto cartSummaryReportDto = new CartSummaryReportDto();
        if (CollectionUtils.isNotEmpty(listOfPossibleDiscounts)) {
            calculateAndUpdatePriceWithDiscount(listOfBooksWithQuantityMap, listOfPossibleDiscounts, cartSummaryReportDto);
        } else {
            calculateAndUpdatePriceWithOutDiscount(listOfBooksWithQuantityMap, cartSummaryReportDto);
        }
        return cartSummaryReportDto;
    }


    public void calculateAndUpdatePriceWithOutDiscount(Map<String, Integer> listOfbooksWithQuantityMap, CartSummaryReportDto priceSummaryDto) {
        BookGroupClassification booksWithoutDiscount = getListOfBookGroupWithoutDiscount(listOfbooksWithQuantityMap);
        List<BookGroupClassification> listOfBookGroupClassification = new ArrayList<>();
        listOfBookGroupClassification.add(booksWithoutDiscount);
        updateBestDiscount(priceSummaryDto, listOfBookGroupClassification);
    }

    public void calculateAndUpdatePriceWithDiscount(Map<String, Integer> listOfbooksWithQuantityMap, List<Integer> listOfPossibleDiscounts, CartSummaryReportDto cartSummaryReportDto) {
        listOfPossibleDiscounts.stream().forEach(numberOfBooksToGroup -> {
            Map<String, Integer> bookQuantityMapCopy = duplicateMap(listOfbooksWithQuantityMap);
            List<BookGroupClassification> listOfBookGroupClassification = getListOfBookGroupWithDiscount(bookQuantityMapCopy, new ArrayList<BookGroupClassification>(), numberOfBooksToGroup);
            if (CollectionUtils.isNotEmpty(bookQuantityMapCopy.keySet())) {
                BookGroupClassification booksWithoutDiscount = getListOfBookGroupWithoutDiscount(bookQuantityMapCopy);
                listOfBookGroupClassification.add(booksWithoutDiscount);
            }
            updateBestDiscount(cartSummaryReportDto, listOfBookGroupClassification);
        });
    }

    private void updateBestDiscount(CartSummaryReportDto priceSummaryDto, List<BookGroupClassification> listOfBookGroupClassification) {
        double discount = listOfBookGroupClassification.stream().mapToDouble(BookGroupClassification::getDiscountAmount).sum();
        if (discount >= priceSummaryDto.getTotalDiscount()) {
            priceSummaryDto.setListOfBookGroupClassifications(listOfBookGroupClassification);
            double actualPrice = listOfBookGroupClassification.stream().mapToDouble(BookGroupClassification::getActualPrice).sum();
            priceSummaryDto.setActualPrice(actualPrice);
            priceSummaryDto.setTotalDiscount(discount);
            priceSummaryDto.setCostEffectivePrice(actualPrice - discount);
        }
    }

    private List<BookGroupClassification> getListOfBookGroupWithDiscount(Map<String, Integer> listOfBooksWithQuantityMap,
                                                                         List<BookGroupClassification> bookGroupClassificationList, int numberOfBooksToGroup) {


        numberOfBooksToGroup = getNumberOfBooksToGroup(listOfBooksWithQuantityMap, numberOfBooksToGroup);
        Optional<DiscountDetailsEnum> discount = getDiscount(numberOfBooksToGroup);
        if (discount.isPresent()) {
            int bookGroupSize = discount.get().getNumberOfDistinctItems();
            List<String> listOfDistinctBooks = listOfBooksWithQuantityMap.keySet().stream().limit(bookGroupSize)
                    .collect(Collectors.toList());
            BookGroupClassification currentBookGroup = getBookGroup(listOfDistinctBooks);
            bookGroupClassificationList.add(currentBookGroup);
            removeDiscountedBooks(listOfBooksWithQuantityMap, listOfDistinctBooks);
            getListOfBookGroupWithDiscount(listOfBooksWithQuantityMap, bookGroupClassificationList, numberOfBooksToGroup);
        }
        return bookGroupClassificationList;
    }


    private int getNumberOfBooksToGroup(Map<String, Integer> bookQuantityMap, Integer numberOfBooksToGroup) {
        return numberOfBooksToGroup < bookQuantityMap.size() ? numberOfBooksToGroup : bookQuantityMap.size();
    }

    private Map<String, Integer> duplicateMap(Map<String, Integer> bookQuantityMap) {

        return bookQuantityMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (firstKey, secondKey) -> secondKey, LinkedHashMap::new));
    }

    public void validateAllBooks(List<BookDto> listOfBooks) {
        Map<String, Double> validBooks = getValidBooks(); //
        List<String> invalidBooks = listOfBooks.stream().filter(book -> !validBooks.containsKey(book.getName())).map(BookDto::getName).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(invalidBooks)) {
            throw new InvalidBookException(invalidBooks);
        }
        List<Integer> invalidQuantities = listOfBooks.stream().filter(book -> book.getQuantity() <= 0).map(BookDto::getQuantity).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(invalidQuantities)) {
            throw new InvalidQuantityException(invalidQuantities);
        }

    }

    private Optional<DiscountDetailsEnum> getDiscount(int numberOfBooks) {

        return Arrays.stream(DiscountDetailsEnum.values()).filter(discountGroup -> discountGroup.getNumberOfDistinctItems() == numberOfBooks).findFirst();
    }

    private BookGroupClassification getListOfBookGroupWithoutDiscount(Map<String, Integer> listOfBooksWithQuantityMap) {
        Map<String, Double> bookIdPriceMap = getValidBooks();
        Set<String> bookTitles = listOfBooksWithQuantityMap.keySet();
        double actualPrice = bookTitles.stream()
                .mapToDouble(bookId -> bookIdPriceMap.get(bookId) * listOfBooksWithQuantityMap.get(bookId)).sum();
        int numberOfBooks = listOfBooksWithQuantityMap.values().stream().mapToInt(Integer::intValue).sum();
        return new BookGroupClassification( new ArrayList<>(listOfBooksWithQuantityMap.keySet()),Constants.ZERO_PERCENT, actualPrice, Constants.NO_DISCOUNT, numberOfBooks);
    }

    private void removeDiscountedBooks(Map<String, Integer> listOfBooksWithQuantityMap, List<String> discountedBooks) {
        discountedBooks.forEach(bookName -> {
            int quantity = listOfBooksWithQuantityMap.get(bookName);
            if (quantity > Constants.ONE_QUANTITY) {
                listOfBooksWithQuantityMap.put(bookName, quantity - Constants.ONE_QUANTITY);
            } else {
                listOfBooksWithQuantityMap.remove(bookName);
            }
        });
    }


    private BookGroupClassification getBookGroup(List<String> listOfBookToGroup) {
        Map<String, Double> bookIdPriceMap = getValidBooks();
        double actualPrice = listOfBookToGroup.stream().mapToDouble(bookId -> bookIdPriceMap.get(bookId) * Constants.ONE_QUANTITY)
                .sum();
        int discountPercentage = getDiscountPercentage(listOfBookToGroup.size());
        double discount = (actualPrice * discountPercentage) / Constants.HUNDRED;
        return new BookGroupClassification(listOfBookToGroup,discountPercentage, actualPrice, discount, listOfBookToGroup.size());

    }

    public List<Integer> getPossibleDiscountValues(int numberOfBooks) {
        return Arrays.stream(DiscountDetailsEnum.values()).sorted(Comparator.reverseOrder()).filter(discountGroup -> discountGroup.getNumberOfDistinctItems() <= numberOfBooks).map(DiscountDetailsEnum::getNumberOfDistinctItems).collect(Collectors.toList());

    }

    private Map<String, Double> getValidBooks() {
        return Arrays.stream(BookStoreEnum.values())
                .collect(Collectors.toMap(BookStoreEnum::getBookTitle, BookStoreEnum::getPrice));
    }

    private int getDiscountPercentage(int numberOfDistinctBooks) {
        Optional<DiscountDetailsEnum> discount = getDiscount(numberOfDistinctBooks);
        return (discount.isPresent()) ? discount.get().getDiscountPercentage() : Constants.ZERO_PERCENT;
    }
}
